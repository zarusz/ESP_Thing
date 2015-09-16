package com.zarusz.control.repository;

import com.zarusz.control.domain.partition.Partition;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Repository
public class PartitionRepositoryImpl extends SimpleJpaRepository<Partition, Integer> implements PartitionRepository {

    private final EntityManager em;

    @Inject
    public PartitionRepositoryImpl(EntityManager em) {
        super(Partition.class, em);
        this.em = em;
    }

    @Override
    public Optional<Partition> findRootFetchChildren() {
        @SuppressWarnings("JpaQlInspection")
        String q = "select p from Partition p join fetch p.children c join fetch p.devices d where p.parent is empty";

        List<Partition> pList = em
                .createQuery(q, Partition.class)
                .getResultList();

        Partition p = pList.size() > 0 ? pList.get(0) : null;
        return Optional.of(p);

        /*
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Partition> cq = cb.createQuery(Partition.class);
        Root<Partition> root = cq.from(Partition.class);
        Partition
                .createQuery(Partition.class)
                .where(save());
        */
    }
}
