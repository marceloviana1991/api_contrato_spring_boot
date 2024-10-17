package contrato.api.repository;

import contrato.api.model.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
    Page<Imovel> findAllByAtivoTrue(Pageable pageable);
}
