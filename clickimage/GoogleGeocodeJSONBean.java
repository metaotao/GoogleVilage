package clickimage;
/**
* @author tao
* @version 1.0
*/

public class GoogleGeocodeJSONBean {	
	public GoogleGeocodeJSONBean(){
	}
	
	public String status;
	public Results[] results;
	
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public Results[] getResults(){
		return results;
	}
	public void setResults(Results[] results){
		this.results=results;
	}
}
