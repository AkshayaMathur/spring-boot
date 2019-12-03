package customerService.Entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CustomerDTO {
	
	private List<Customer> custList;
	
	public CustomerDTO() {}

	public CustomerDTO(List<Customer> custList) {
		super();
		this.custList = custList;
	}

	public List<Customer> getCustList() {
		return custList;
	}

	public void setCustList(List<Customer> custList) {
		this.custList = custList;
	}
	
	

}
