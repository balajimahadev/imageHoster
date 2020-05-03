package ImageHoster.repository;

import ImageHoster.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CommentRepository {

    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Comment postComment(Comment newCom){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(newCom);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return newCom;
    }

    public List<Comment> getComments(Integer imageId, String imgTitle) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Comment> query = em.createQuery("SELECT c from Comment c where c.image.id =:imageId",
                                                                Comment.class).setParameter("imageId", imageId);
            List<Comment> resultList = query.getResultList();
            return resultList;
        }
        catch (NoResultException e){
            return null;
        }
    }

}
