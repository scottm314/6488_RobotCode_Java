package Example1;

/* this is the test class that is used in the program.   Look at public double getD()
 * it looks like is should increment but it doesn't.  Why!???
 */

class testClass {

	    private int i;
	    private double d;

	    public testClass() {
	    		System.out.println("construct with no variable"); 
	    		i=0; d=0.0;}  // constructor
	    public testClass(int pass) {
	    		System.out.println("construct with an int");
	    		i=pass;d=0.0;}  
	    public testClass(double passd) {
	    		System.out.println("contruct with a double");
	    		i=0;d=passd;}
	    
/*  below are the methods for this class   */
	    public int getI() { return i;}         // a method to return the default value
	    public void setI(int pass) {i=pass;}  // a method to return the value that was passed...
	    public double getD() {
	    		double e=0;
	    		e=e+d;
	    		return e;} // look, I try to increment d!
	    public void setD(double passd) {d=passd;}

	}

