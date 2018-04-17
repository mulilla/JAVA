package autobotiga;

public interface Persistent {
	/**
	 * Afegeix un vehicle a la taula si no existeix ja
	 * @param vehicle
	 * @return true, si l'ha afegit
	 * 			o false, si no
	 */
	public boolean guardar(Vehicle vehicle);
	
	/**
	 * Elimina el vehicle de la taula que coincideixi amb la matrícula
	 * @param matricula
	 * @return true, si l'ha eliminat
	 * 			o false, si no
	 * @throws Exception 
	 */
	public boolean eliminar(String matricula) throws Exception;
	
	/**
	 * Busca un vehicle a la taula a partir de la seva matrícula
	 * @param matricula
	 * @return El vehicle trovat
	 * 			0 null, si no el trova
	 */
	public Vehicle buscar(String matricula);
    
    /**
     * Imprimeix tots els vehicles de la taula
     */
    public void imprimirTots();
}
