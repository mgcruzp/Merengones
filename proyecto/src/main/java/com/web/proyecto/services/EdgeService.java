package com.web.proyecto.services;
package com.web.proyecto.services;

import com.web.proyecto.entities.Edge;
import com.web.proyecto.entities.Process;
import com.web.proyecto.repositories.EdgeRepository;
import com.web.proyecto.repositories.ProcessRepository;
import com.web.proyecto.repositories.NodeRepository;
import java.util.List;
import java.util.Optional;

//	Crear EdgeService con: 
// create(processId,sourceId,targetId), listByProcess(processId), delete(id).
// existe processId/sourceId/targetId; source/target pertenecen al mismo process; sin self-loop (source!=target).

public class EdgeService {

    private final EdgeRepository edgeRepository;
    private final ProcessRepository processRepository;
    private final NodeRepository nodeRepository;

    public EdgeService(EdgeRepository edgeRepository, ProcessRepository processRepository, NodeRepository nodeRepository) {
        this.edgeRepository = edgeRepository;
        this.processRepository = processRepository;
        this.nodeRepository = nodeRepository;
    }

    public Edge create(Long processId, Long sourceId, Long targetId) {
        Optional<Process> process = processRepository.findById(processId);
        if (process.isEmpty()) {
            throw new IllegalArgumentException("Proceos " + processId + " no encontrado");
        }

        Optional<Node> sourceNode = nodeRepository.findById(sourceId);
        if (sourceNode.isEmpty()) {
            throw new IllegalArgumentException("Nodo origen " + sourceId + "  no encontrado");
        }

        Optional<Node> targetNode = nodeRepository.findById(targetId);
        if (targetNode.isEmpty()) {
            throw new IllegalArgumentException("Nodo objetivo " + targetId + "  no encontrado");
        }

        // Validate that source and target belong to the same process
        if (!sourceNode.get().getProcessId().equals(processId)) {
            throw new IllegalStateException("El nodo de origen no pertenece al proceso ");
        }

        if (!targetNode.get().getProcessId().equals(processId)) {
            throw new IllegalStateException("El nodo objetivo no pertenece al proceso especificado");
        }

        if (sourceId.equals(targetId)) {
            throw new IllegalStateException("El origen y el destino no pueden ser los mismos");
        }

        Edge edge = new Edge(processId, sourceId, targetId);
        return edgeRepository.save(edge);
    }

    public List<Edge> listByProcess(Long processId) {
        List<Edge> edges = edgeRepository.findByProcessId(processId);
        if (edges.isEmpty()) {
            throw new IllegalStateException("No se encontraron edges con el ID " + processId);
        }
        return edges;
    }

    public void delete(Long id) {
        Optional<Edge> edge = edgeRepository.findById(id);
        if (edge.isEmpty()) {
            throw new IllegalArgumentException("Edge  " + id + " no encontrado");
        }
        edgeRepository.deleteById(id);
    }
}
