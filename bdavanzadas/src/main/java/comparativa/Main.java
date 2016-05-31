package comparativa;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	
	//Numero de consultas maximo a realizar
	private final int maxConsultas = 100;
	
	// Lista de hilos para iniciarlos y esperar a que terminen todos
	private final ArrayList<Thread> threads; 
	
	private long max;
	private long min;

	enum TipoTiempo{
		INSERT_MYSQL, UPDATE_MYSQL, SELECT_MYSQL, INSERT_MONGO, UPDATE_MONGO, SELECT_MONGO
	}
	
	//Variable para almacenar todos los tiempos
	private final HashMap<TipoTiempo, ArrayList<Long>> tiempos;
	private final HashMap<TipoTiempo, Long> totales;
	
	
	
	public Main(){
		 threads = new ArrayList<Thread>();
		 tiempos = new HashMap<TipoTiempo,ArrayList<Long>>();
		 this.totales = new HashMap<TipoTiempo, Long>();
		 
		 for(TipoTiempo tipo : TipoTiempo.values()){
			 this.tiempos.put(tipo, new ArrayList<Long>());
			 this.totales.put(tipo, 0l);
		 }
	}

	
	
	private void insertMySQL(){
		Thread t = new Thread(){
			public void run(){
				int i=0;
				while(i<maxConsultas){
					// Tiempo inicial de referencia
					long initialTime = System.currentTimeMillis();
					i++;
					System.out.println("InsertMysql");
					//TODO Agregar consulta
					try {//Quitar cuando consulta ya este lista
						Thread.sleep((long) (Math.random()*1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					long finalTime = System.currentTimeMillis();
					agregarTiempo(TipoTiempo.INSERT_MYSQL, finalTime-initialTime);
				}
			}
		};
		this.threads.add(t);
	}
	
	/**
	 * Metodo para controlar los totales de los tiempos
	 * @param tipo
	 * @param tiempo
	 */
	private void agregarTiempo(TipoTiempo tipo, long tiempo){
		this.tiempos.get(tipo).add(tiempo);
		this.totales.put(tipo, this.totales.get(tipo)+tiempo);//LLevar el total
		if(tiempo>max){ //Obtener el maximo
			max = tiempo;
		}
		if(tiempo<min || min==0){ // Obtener el minimo
			min = tiempo;
		}
		
	}
	
	private void insertMongo(){
		Thread t = new Thread(){
			public void run(){
				int i=0;
				while(i<maxConsultas){
					// Tiempo inicial de referencia
					long initialTime = System.currentTimeMillis();
					i++;
					System.out.println("InsertMongo");
					try {
						Thread.sleep((long) (Math.random()*1000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long finalTime = System.currentTimeMillis();
					agregarTiempo(TipoTiempo.INSERT_MONGO, finalTime-initialTime);
				}
			}
		};
		this.threads.add(t);
	}
	
	private void updateMySQL(){
		Thread t = new Thread(){
			public void run(){
				int i=0;
				while(i<maxConsultas){
					// Tiempo inicial de referencia
					long initialTime = System.currentTimeMillis();
					i++;
					System.out.println("UPDATE MYSQL");
					//TODO Agregar consulta
					try {//Quitar cuando consulta ya este lista
						Thread.sleep((long) (Math.random()*1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					long finalTime = System.currentTimeMillis();
					agregarTiempo(TipoTiempo.UPDATE_MYSQL, finalTime-initialTime);
				}
			}
		};
		this.threads.add(t);
	}
	
	private void updateMongo(){
		Thread t = new Thread(){
			public void run(){
				int i=0;
				while(i<maxConsultas){
					// Tiempo inicial de referencia
					long initialTime = System.currentTimeMillis();
					i++;
					System.out.println("UPDATE MONGO");
					//TODO Agregar consulta
					try {//Quitar cuando consulta ya este lista
						Thread.sleep((long) (Math.random()*1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					long finalTime = System.currentTimeMillis();
					agregarTiempo(TipoTiempo.UPDATE_MONGO, finalTime-initialTime);
				}
			}
		};
		this.threads.add(t);
	}
	
	private void selectMySQL(){
		Thread t = new Thread(){
			public void run(){
				int i=0;
				while(i<maxConsultas){
					// Tiempo inicial de referencia
					long initialTime = System.currentTimeMillis();
					i++;
					System.out.println("SELECT MYSQL");
					//TODO Agregar consulta
					try {//Quitar cuando consulta ya este lista
						Thread.sleep((long) (Math.random()*1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					long finalTime = System.currentTimeMillis();
					agregarTiempo(TipoTiempo.SELECT_MYSQL, finalTime-initialTime);
				}
			}
		};
		this.threads.add(t);
	}
	
	private void selectMongo(){
		Thread t = new Thread(){
			public void run(){
				int i=0;
				while(i<maxConsultas){
					// Tiempo inicial de referencia
					long initialTime = System.currentTimeMillis();
					i++;
					System.out.println("SELECT MONGO");
					//TODO Agregar consulta
					try {//Quitar cuando consulta ya este lista
						Thread.sleep((long) (Math.random()*1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					long finalTime = System.currentTimeMillis();
					agregarTiempo(TipoTiempo.SELECT_MONGO, finalTime-initialTime);
				}
			}
		};
		this.threads.add(t);
	}
		
	
	/**
	 * Metodo principal encargado de administrar la ejecución
	 */
	public void medirTiempos(){
		
		this.insertMySQL();
		this.insertMongo();
		this.updateMongo();
		this.updateMySQL();
		this.selectMongo();
		this.selectMySQL();
		
		for(Thread t : this.threads){
			t.start();
		}
		
		// Tiempo inicial de referencia
		long initialTime = System.currentTimeMillis();
		for(Thread t : this.threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long finalTime = System.currentTimeMillis();
		//(System.currentTimeMillis() - this.initialTime) / 1000
		System.out.println("Total time: "+(finalTime-initialTime));
		
		for(TipoTiempo tipo : TipoTiempo.values()){
			System.out.println("Tiempos: "+this.tiempos.get(tipo));
			System.out.println("Total: "+this.totales.get(tipo));
		}
		
	}
	
	public static void main(String[] args) {
		new Main().medirTiempos();
	}

}
