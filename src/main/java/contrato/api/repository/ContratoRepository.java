package contrato.api.repository;

import contrato.api.model.Contrato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    @Query("select c from Contrato c where c.imovel.ativo")
    Page<Contrato> contratosAtivos(Pageable pageable);
}
