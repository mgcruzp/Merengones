// contacto.js
document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById('contactForm');
  if (!form) return;

  // Campos y reglas
  const fields = {
    nombre:      { el: document.getElementById('nombre'),      max: 100, noDiacritics: true, required: true },
    apellido:    { el: document.getElementById('apellido'),    max: 100, noDiacritics: true, required: true },
    correo:      { el: document.getElementById('correo'),      max: 100, required: true, email: true },
    semestre:    { el: document.getElementById('semestre'),    required: true, min: 0, max: 16 },
    descripcion: { el: document.getElementById('descripcion'), max: 300, required: true }
  };

  // Mapas de contadores (span[data-count-for="..."]) y contenedores de error (div[data-error-for="..."])
  const counters = {};
  document.querySelectorAll('[data-count-for]').forEach(el => {
    const key = el.dataset.countFor;
    if (key) counters[key] = el;
  });

  const errors = {};
  document.querySelectorAll('.error').forEach(el => {
    const key = el.dataset.errorFor || el.dataset.error;
    if (key) errors[key] = el;
  });

  // Helpers de normalización
  function normalizeNoDiacritics(str) {
    return str
      .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
      .replace(/ñ/g, 'n').replace(/Ñ/g, 'N')
      .toUpperCase();
  }
  function normalizeEmail(str) { return str.replace(/\s+/g, '').toUpperCase(); }

  // Correo válido: sin espacios, con @ y un punto después del @
  function isValidEmailStrict(value) {
    if (!value || /\s/.test(value)) return false;
    const at = value.indexOf('@');
    if (at < 1) return false;
    const dot = value.indexOf('.', at + 2);
    return dot > at + 1 && dot < value.length - 1;
  }

  // Actualiza contador (solo el número "actual". El "/MAX" está en el HTML)
  function updateCounter(key) {
    const f = fields[key];
    if (!f || !f.el || !counters[key] || !f.max) return;
    counters[key].textContent = String(f.el.value.length);
  }

  // Asegura que exista el contenedor de error (si falta, lo crea dentro del .form-group)
  function ensureErrorContainer(key) {
    if (errors[key]) return errors[key];
    const f = fields[key];
    if (!f || !f.el) return null;

    const group = f.el.closest('.form-group') || f.el.parentElement;
    let box = group.querySelector(`.error[data-error-for="${key}"]`) || group.querySelector(`.error[data-error="${key}"]`);

    if (!box) {
      box = document.createElement('div');
      box.className = 'error';
      box.setAttribute('data-error-for', key);
      group.appendChild(box);
    }
    errors[key] = box;
    return box;
  }

  function setError(key, msg) {
    const f = fields[key];
    if (!f || !f.el) return;
    const group = f.el.closest('.form-group') || f.el.parentElement;

    group.classList.add('invalid');
    f.el.classList.add('is-invalid');
    f.el.classList.remove('is-valid');

    const box = ensureErrorContainer(key);
    if (box) box.textContent = msg || 'Campo inválido.';
  }

  function clearError(key) {
    const f = fields[key];
    if (!f || !f.el) return;
    const group = f.el.closest('.form-group') || f.el.parentElement;

    group.classList.remove('invalid');
    f.el.classList.remove('is-invalid');
    f.el.classList.add('is-valid');

    const box = ensureErrorContainer(key);
    if (box) box.textContent = '';
  }

  function validateField(key) {
    const f = fields[key];
    if (!f || !f.el) return true;

    const v = f.el.value;

    if (f.required && !v.trim()) {
      setError(key, 'Este campo es obligatorio.');
      return false;
    }

    if (f.max && v.length > f.max) {
      setError(key, `Máximo ${f.max} caracteres.`);
      return false;
    }

    if (key === 'correo') {
      if (!isValidEmailStrict(v)) {
        setError(key, 'Correo inválido (debe contener "@" y un punto después, sin espacios).');
        return false;
      }
    }

    if (key === 'semestre') {
      const num = Number(v);
      if (Number.isNaN(num) || num < f.min || num > f.max) {
        setError(key, `Debe ser un número entre ${f.min} y ${f.max}.`);
        return false;
      }
    }

    clearError(key);
    return true;
  }

  // Listeners: bloqueo al máximo, normalización, contador, validación en vivo
  Object.keys(fields).forEach(key => {
    const f = fields[key];
    if (!f.el) return;

    // Bloqueo al llegar al máximo (permite borrar/navegar/pegar sobre selección)
    f.el.addEventListener('keydown', (e) => {
      if (!f.max) return;
      const allowed = ['Backspace','Delete','ArrowLeft','ArrowRight','Tab','Home','End','Enter'];
      const isShortcut = e.ctrlKey || e.metaKey;
      const hasSelection = f.el.selectionStart !== f.el.selectionEnd;
      if (!allowed.includes(e.key) && !isShortcut && !hasSelection && f.el.value.length >= f.max) {
        e.preventDefault();
      }
    });

    f.el.addEventListener('input', () => {
      // Normalización por campo
      if (f.noDiacritics) {
        const pos = f.el.selectionStart;
        f.el.value = normalizeNoDiacritics(f.el.value);
        try { f.el.setSelectionRange(pos, pos); } catch (_) {}
      } else if (key === 'correo') {
        const pos = f.el.selectionStart;
        f.el.value = normalizeEmail(f.el.value);
        try { f.el.setSelectionRange(pos, pos); } catch (_) {}
      } else {
        f.el.value = f.el.value.toUpperCase();
      }

      // Salvavidas para pegado
      if (f.max && f.el.value.length > f.max) {
        f.el.value = f.el.value.slice(0, f.max);
      }

      updateCounter(key);
      validateField(key);
    });

    // Inicializa contador
    updateCounter(key);
  });

  // Validación final al enviar
  form.addEventListener('submit', (e) => {
    let ok = true;

    Object.keys(fields).forEach(key => {
      const f = fields[key];
      if (!f.el) return;

      // Normalización final
      if (f.noDiacritics)        f.el.value = normalizeNoDiacritics(f.el.value);
      else if (key === 'correo') f.el.value = normalizeEmail(f.el.value);
      else                       f.el.value = f.el.value.toUpperCase();

      updateCounter(key);
      if (!validateField(key)) ok = false;
    });

    if (!ok) {
      e.preventDefault();
      const firstErr = document.querySelector('.is-invalid');
      if (firstErr) firstErr.focus();
    }
  });
});
