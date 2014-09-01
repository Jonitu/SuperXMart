package br.com.superxmart.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.superxmart.entidade.Mapa;

@SuppressWarnings("deprecation")
public class RotaDAO {

	private SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();

	public Mapa salvarMapa(Mapa pMapa) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Mapa mapa = null;
		try {

			mapa = (Mapa) session.merge(pMapa);
			session.persist(mapa);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}

		return mapa;
	}

	public Mapa buscarMapaPorNome(String nomeMapa) {
		String hql = "FROM Mapa WHERE nome = :nomeMapa";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setString("nomeMapa", nomeMapa);
		return (Mapa) query.uniqueResult();
	}

}
