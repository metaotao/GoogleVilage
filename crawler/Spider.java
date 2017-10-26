import org.jsoup.Jsoup;  
import org.jsoup.Connection;
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.net.MalformedURLException;

public class Spider{
	private List<String> urlList=new LinkedList<String>();	
	private StringBuffer sb=new StringBuffer();
	public Spider(){	
		try{
			getHtml("http://www.xzqh.org/html/list/139.html");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public String getUrl(){
		return urlList.remove(0);
	}

	public boolean isEmpty(){
		if(urlList.size()!=0){
			return true;
		}
		else{
			return false;
		}
	}

	public void getAllUrl(String url){		
		try{
			Document doc=null;
			Connection conn=Jsoup.connect(url);
			conn.timeout(3000);
			doc=conn.get();
			Element body=doc.body();
			Elements es=body.select("a");
			for(Iterator it=es.iterator();it.hasNext();){
				Element e=(Element)it.next();
				String s=e.attr("href");
				String[] split=s.split("\\/");
				if(s.endsWith(".html")&&split.length>2&&split[1].equals("ah")){
					urlList.add("http://www.xzqh.org/html/"+s);
				}
							
			}
		}	
		catch(IOException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getHtml(String url){
		getAllUrl(url);
		while(isEmpty()){
			Document doc=null;
			String title=null;
			try{
				String httpUrl=getUrl();
				
				doc=Jsoup.connect(httpUrl).get();
				title=doc.title();
				if(title.contains("Õò")||title.contains("Ïç")){
					System.out.println(title);
					Elements divs=doc.select("div.content");
					StringBuilder builder=new StringBuilder();
					if(divs!=null){
						String s=null;
						for(Element div:divs){
							s=div+"";
						//	System.out.println(s);
						}
						String[] splits=s.split("<br />");
						for(int i=1;i<splits.length-1;i++){
							System.out.println(splits[i]);
						}
						String[] split=splits[splits.length-1].split("<div");
						System.out.println(split[0]);
					}				
				}			
			} 
			catch(MalformedURLException e){  
				e.printStackTrace();  
			} 
			catch(IOException e){  
				e.printStackTrace();  
			}  
		}
	}

	public static void main(String[] args){
		new Spider();
	}

}
