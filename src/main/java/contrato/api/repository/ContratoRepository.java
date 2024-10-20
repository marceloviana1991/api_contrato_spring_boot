package contrato.api.repository;

import contrato.api.model.Contrato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    @Query("select c from Contrato c where c.imovel.ativo")
    Page<Contrato> contratosAtivos(Pageable pageable);

    @Query("select c from Contrato c " +
            "where (c.dataInicio <= :dataInicio AND c.dataFinalizacao >= :dataInicio AND c.imovel.id = :idImovel)" +
            "OR (c.dataInicio <= :dataFinalizacao AND c.dataFinalizacao >= :dataFinalizacao AND c.imovel.id = :idImovel)" +
            "OR (c.dataInicio >= :dataInicio AND c.dataFinalizacao <= :dataFinalizacao AND c.imovel.id = :idImovel)")
    List<Contrato> contratosChoqueDeDatas(LocalDate dataInicio, LocalDate dataFinalizacao, Long idImovel);

    @Query("select c from Contrato c " +
            "where (c.dataInicio <= :dataInicio AND c.dataFinalizacao >= :dataInicio AND c.imovel.id = :idImovel AND c.id <> :id)" +
            "OR (c.dataInicio <= :dataFinalizacao AND c.dataFinalizacao >= :dataFinalizacao AND c.imovel.id = :idImovel AND c.id <> :id)" +
            "OR (c.dataInicio >= :dataInicio AND c.dataFinalizacao <= :dataFinalizacao AND c.imovel.id = :idImovel AND c.id <> :id)")
    List<Contrato> contratosChoqueDeDatasAtualizar(LocalDate dataInicio, LocalDate dataFinalizacao, Long idImovel, Long id);
}
