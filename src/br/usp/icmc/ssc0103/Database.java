package br.usp.icmc.ssc0103;


class User{

}

class Book{

}

class Loan{

	to string 
	to csv
}



public class Database extends  {

	List<User> users;
	List<Book> books;
	List<Loan> loans;
	
	String userfile;
	String bookfile;
	String loanfile;
	
	
	
	public Detabase(String userfile, String bookfile, String loanfile){
		// se nao exister cria os arquivos;
		// carregar as listas
		// seta os this.files
		// 
	}
	private Serialize(String filename, String data){
		// atualiza os arquivos
	}
	public void UserAdd(String username, int usertype) {
	//add na lista
	//atualiaza o csv File, serializa(this.userfile, user.toString)
	}
	public void CatalogAdd(String bookname, int booktype){
	//igual o de cima	
	}
	
	public void CheckIn(String bookname, Date date) throws DatabseException { 
		
		//se nao tiver emprestrado
		// 
	}
	
	public void CheckOut(String booknmae, String username, Date date,) throws
							DatabseExpection, AccessExpection, AvailabilityExpection {
	}
	
	
	public void List(String... names) {
		//local method list
		for (String name : names) {
			if (name.equals("users")){
				//list user 
			}
			//bla bla
		}
	}
	
	
	
	
}
