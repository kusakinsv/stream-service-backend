//package ru.one.stream.server.repositories;
//
//import jakarta.transaction.Transactional;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Repository;
//import ru.one.stream.server.entities.MusicTrack;
//import ru.one.stream.server.entities.Pattern;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Repository
//@Transactional
//public class MusicTrackFullTextRepository implements ApplicationListener<ApplicationReadyEvent> {
//
//    @PersistenceContext
//    EntityManager entityManager;
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//        try {
//            fullTextEntityManager.createIndexer().startAndWait();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(("An error occurred trying to build the search index: " + e.toString()));
//        }
//    }
//
//    public List<MusicTrack> findMusicTrackByPatternTitle(String name) {
//        name = name.toLowerCase();
//        Set<MusicTrack> foundedTracks = new HashSet<>();
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
//                .forEntity(Pattern.class).get();
//        Query query = queryBuilder
//                .phrase()
//                .onField("title")
//                .sentence(name)
//                .createQuery();
//        FullTextQuery jpaQuery  = fullTextEntityManager.createFullTextQuery(query, Pattern.class);
//        List<Pattern> results = jpaQuery.getResultList();
//        results.forEach(pattern -> foundedTracks.addAll(pattern.getTracks()));
//        return new ArrayList<>(foundedTracks);
//    }
//
//
//}
