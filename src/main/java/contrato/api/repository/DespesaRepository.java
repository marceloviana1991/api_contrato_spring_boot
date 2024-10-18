package contrato.api.repository;

import contrato.api.model.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query("select d from Despesa d where d.imovel.ativo")
    Page<Despesa> despesasAtivas(Pageable pageable);
}
