package Example1;

/* I have created a test program to illustarte a couple of java principles.  We learn how to
 * declare variables i and mainD
 * construct a class that allows different types to be passed to it (testClass)
 * access information from the class (getD gets the double passed to the testClass
 * the syntax for building a for loop.
 * and, we have a bug .. why doesn't mainD increment (e=e+d is in the class getD)???
 * the last part is the puzzle!!!!! 
 */


public class Example1 {
	
	public static void main(String[] args) {
		
		int i=0;
		double mainD;
		
		// this is where it all starts.  Lets print a message to the console
		System.out.println("Hello World!: ");
		
		/** next step, lets look at what happens when we construct a class
		 * we are not going to worry about this too much right now, but it shows 
		 * that we can call a class with different numbers and types of arguments and the
		right constructor is called    **/	
		testClass test = new testClass(5.0);   
		testClass test2 = new testClass(5);   
		testClass test3 = new testClass(); 
		System.out.println("return from testClass " + test.getD());
		
		/* this part of the progarm is meant to illustarte the bug we found at robotics
		 * the issue is that we are trying to increment a value around a loop but it does 
		 * not work.  Why!?  This is an important concept to understand.....
		 */
		for(i=0; i<10; ++i) {
			mainD = test.getD();
			System.out.println("return from testClass " + mainD);
		}  // end for loop
	} // end main block
}// end Example class block
	
