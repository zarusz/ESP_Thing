package com.zarusz.control.repository.jpa;

import com.zarusz.control.domain.partition.Partition;
import com.zarusz.control.repository.PartitionRepository;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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
        //String q = "select p from Partition p left join fetch p.children c left join fetch p.devices d where p.parent is empty";
        String q = "select p from Partition p left join fetch p.parent parent where p.parent is empty";

        Partition p = em
                .createQuery(q, Partition.class)
                .getSingleResult();

        if (p != null) {
            CollectionInit.Fetch(p, x -> x.getChildren(), x -> Hibernate.initialize(x.getDevices()));
        }

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
