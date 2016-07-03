package ms.eureka.client.sentence.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SentenceRestController {

	@Autowired private DiscoveryClient client;
	
	@RequestMapping("/sentence")
	public @ResponseBody String getSentence() {
	    
		return 
	      getWord("ms-eureka-client-subject") + " "+
	      getWord("ms-eureka-client-verb") + " "+
	      getWord("ms-eureka-client-article") + " "+
	      getWord("ms-eureka-client-adjective") + " "+
	      getWord("ms-eureka-client-noun") + ".";
	}

	public String getWord(String service) {
		
		try{
		List<ServiceInstance> list = client.getInstances(service);
    
		    if (list != null && list.size() > 0 ) {
		      URI uri = list.get(0).getUri();
		      if (uri !=null ) {
		    	  return (new RestTemplate()).getForObject(uri,String.class);
		      }
		    }else{
		    	return "[ "+service + " not registered ]";
		    }
	    
		}catch(Exception e){
			System.out.println(e);
			
		}
	    return null;
	    
	}
	  
}
