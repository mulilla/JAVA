package modelo;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class Productos {
	private EntityManager em;
	
	
	public Productos(EntityManager em) {
		this.em=em;
	}


	public void addProducto(Producto productemp) {
		
		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		
		if(em.find(Producto.class, productemp.getId()) != null){
			em.merge(productemp);
			tx.commit();
			
		}else{
			em.persist(productemp);
			tx.commit();
			
		}

	}
	

	@SuppressWarnings("resource")
	public Producto searchProducto(String id) {
		return em.find(Producto.class, id);
		
	}
	
	
	
	
	public boolean deleteProducto(String id){
		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		em.remove(em.find(Producto.class, id));
		em.flush();
		tx.commit();
		return true;
		
	}
	
	
	
	
	public void closeDB(){
		if(em!=null) em.close();	
	}
}
