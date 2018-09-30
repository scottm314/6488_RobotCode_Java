package Example1;

/* this is the test class that is used in the program.   Look at public double getD()
 * it looks like is should increment but it doesn't.  Why!???
 */

class testClass {

	private int i;
	private double d;
	
	/**
	Default constructor of testClass
	*/
	public testClass() {
		System.out.println("construct with no variable"); 
	    	i=0; 
		d=0.0;
	}
	
	/**
	Integer constructor of testClass
	*/
	public testClass(int pass) {
		System.out.println("construct with an int");
	    	i=pass;
		d=0.0;
	} 
	
	/**
	Double constructor of testClass
	*/
	public testClass(double passd) {
		System.out.println("contruct with a double");
	    	i=0;
		d=passd;
	}
	    
/*  below are the methods for this class   */
	
	/**
	A method to return the default value
	@return the default value i
	*/
	public int getI() { 
		return i;
	}
	
	/**
	A method to return the value passed
	@param pass An integer you want returned
	@return The value passed in
	*/
	public void setI(int pass) {
		i=pass;
	}
	
	/**
	A method that increments d
	@return An incremented version of d
	*/
	public double getD() {
		double e=0;
		e=e+d;
	    	return e; // look, I try to increment d!
	} 
	
	/**
	Sets the value of d
	@param passd The new value of d
	*/
	public void setD(double passd) {
		d=passd;
	}

}

