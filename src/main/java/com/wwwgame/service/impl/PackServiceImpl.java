package com.wwwgame.service.impl;

import com.wwwgame.entity.Pack;
import com.wwwgame.entity.User;
import com.wwwgame.repository.PackRepository;
import com.wwwgame.service.PackService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PackServiceImpl implements PackService {

    private final PackRepository packRepository;

    @PersistenceContext
    private EntityManager em;

    public PackServiceImpl(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @CacheEvict(value = "packs", allEntries = true)
    @Override
    public void createPack(Pack pack) {
        packRepository.save(pack);
    }

    @Transactional
    @CacheEvict(value = "packs", allEntries = true)
    @Override
    public void deletePack(Pack pack) {
        Long packId = pack.getId();

        em.createNativeQuery(
                "DELETE FROM chatmessages WHERE tournament_id IN " +
                "(SELECT id FROM tournaments WHERE pack_id = :packId)")
            .setParameter("packId", packId)
            .executeUpdate();

        em.createNativeQuery(
                "DELETE FROM tournamentplayer WHERE tournament_id IN " +
                "(SELECT id FROM tournaments WHERE pack_id = :packId)")
            .setParameter("packId", packId)
            .executeUpdate();

        em.createNativeQuery(
                "DELETE FROM tournaments WHERE pack_id = :packId")
            .setParameter("packId", packId)
            .executeUpdate();

        em.createNativeQuery(
                "DELETE FROM user_favorite_packs WHERE pack_id = :packId")
            .setParameter("packId", packId)
            .executeUpdate();

        em.createQuery(
                "DELETE FROM UserAnswer ua WHERE ua.question IN " +
                "(SELECT q FROM Question q WHERE q.pack.id = :packId)")
            .setParameter("packId", packId)
            .executeUpdate();

        em.createQuery("DELETE FROM Question q WHERE q.pack.id = :packId")
            .setParameter("packId", packId)
            .executeUpdate();

        em.createQuery("DELETE FROM Pack p WHERE p.id = :packId")
            .setParameter("packId", packId)
            .executeUpdate();
    }

    @Cacheable("packs")
    @Override
    public List<Pack> findAll() {
        return packRepository.findAll();
    }

    @Override
    public List<Pack> findByAuthor(User author) {
        return packRepository.findPackByAuthor(author);
    }

    @Override
    public Pack findById(Long id) {
        return packRepository.findById(id).orElseThrow();
    }

    @CacheEvict(value = "packs", allEntries = true)
    @Override
    public void renamePack(Pack pack, String newName) {
        pack.setName(newName);
        packRepository.save(pack);
    }
}
