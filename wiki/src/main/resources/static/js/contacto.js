// contacto.js
document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById('contactForm');

  // Campos y reglas
  const fields = {
    nombre: {
      el: document.getElementById('nombre'),
      max: 100,
      noDiacritics: true,
      required: true
    },
    apellido: {
      el: document.getElementById('apellido'),
      max: 100,
      noDiacritics: true,
      required: true
    },
    correo: {
      el: document.getElementById('correo'),
      max: 100,
      required: true,
      email: true
    },
    semestre: {
      el: document.getElementById('semestre'),
      required: true,
      min: 0,
      max: 16
    },
    descripcion: {
      el: document.getElementById('descripcion'),
      required: true
    }
  };

  // Mapas de contadores y contenedores de error
  const counters = {};
  document.querySelectorAll('.counter').forEach(c => {
    counters[c.dataset.for] = c;
  });

  const errors = {};
  document.querySelectorAll('.error').forEach(e => {
    errors[e.dataset.error] = e;
  });

  // Helpers
  function normalizeNoDiacritics(str) {
    // Elimina tildes y cambia ñ/Ñ por N/n, luego mayúsculas
    return str
      .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
      .replace(/ñ/g, 'n').replace(/Ñ/g, 'N')
      .toUpperCase();
  }

  function normalizeEmail(str) {
    return str.replace(/\s+/g, '').toUpperCase();
  }

  // Correo válido: sin espacios, con @ y un punto después del @
  function isValidEmailStrict(value) {
    if (!value || /\s/.test(value)) return false;
    const at = value.indexOf('@');
    if (at < 1) return false;
    const dot = value.indexOf('.', at + 2);
    return dot > at + 1 && dot < value.length - 1;
  }

  function updateCounter(key) {
    const f = fields[key];
    if (!f || !f.max || !counters[key]) return;
    counters[key].textContent = `${f.el.value.length}/${f.max}`;
  }

  function setError(key, msg) {
    const f = fields[key];
    if (!f) return;
    f.el.classList.add('is-invalid');
    f.el.classList.remove('is-valid');
    if (errors[key]) errors[key].textContent = msg || '';
  }

  function clearError(key) {
    const f = fields[key];
    if (!f) return;
    f.el.classList.remove('is-invalid');
    f.el.classList.add('is-valid');
    if (errors[key]) errors[key].textContent = '';
  }

  function validateField(key) {
    const f = fields[key];
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

  // Listeners de entrada: normalización, contador y validación en vivo
  Object.keys(fields).forEach(key => {
    const f = fields[key];

    f.el.addEventListener('input', () => {
      // Normalización por campo
      if (f.noDiacritics) {
        const pos = f.el.selectionStart;
        f.el.value = normalizeNoDiacritics(f.el.value);
        // mantiene posición del cursor
        try { f.el.setSelectionRange(pos, pos); } catch (_) {}
      } else if (key === 'correo') {
        const pos = f.el.selectionStart;
        f.el.value = normalizeEmail(f.el.value);
        try { f.el.setSelectionRange(pos, pos); } catch (_) {}
      } else {
        f.el.value = f.el.value.toUpperCase();
      }

      // Límite duro de caracteres
      if (f.max && f.el.value.length > f.max) {
        f.el.value = f.el.value.slice(0, f.max);
      }

      updateCounter(key);
      validateField(key);
    });

    // Inicializa contador
    updateCounter(key);
  });

  // Validación al enviar
  form.addEventListener('submit', (e) => {
    let ok = true;

    Object.keys(fields).forEach(key => {
      const f = fields[key];
      // Normalización final
      if (f.noDiacritics)      f.el.value = normalizeNoDiacritics(f.el.value);
      else if (key === 'correo') f.el.value = normalizeEmail(f.el.value);
      else                      f.el.value = f.el.value.toUpperCase();

      updateCounter(key);
      if (!validateField(key)) ok = false;
    });

    if (!ok) {
      e.preventDefault(); // Evita enviar si hay errores
      // Lleva foco al primer campo con error
      const firstErr = document.querySelector('.is-invalid');
      if (firstErr) firstErr.focus();
    }
  });
});
