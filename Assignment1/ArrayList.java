import java.util.*;

public class ArrayList{
	//declaring variables
	double array_1by4; 
	double array_3by4;
	int arraysize;
	int top;
	int delete;
	static int z;
	static double length;
	static int Array[];

	//constructor
	public ArrayList()
	{
		array_1by4 = 0.25;
		array_3by4 = 0.75;
		arraysize = 4;
		top = 0;
		Array = new int[arraysize];
	}

	//This function adds element to the array
	public void add(int e)
	{
		Array[top] = e;
		top++;
	}

	//This function deletes the last element in the array
	public void pop()
	{
		top--;
		delete = Array[top];
		Array[top] = 0;
		int count = 0;

		for(int i = 0; i < Array[i]; i++){
			count++;
		}
		z = count;
		length = (double)z/arraysize;

		System.out.println("Deleted Element: " +delete);
	}
	
	//This function changes the size of the array
	public void resize()
	{
		//When the array is 1/4th full, the array size is halved
		if(length == array_1by4 ){
			int newArray[] = new int[(arraysize/2)*2];

			for(int i = 0; i < Array.length; i++) {
				newArray[i] = Array[i];
			}
			Array = newArray;
			arraysize = arraysize/2;

			for(int n : newArray){
				System.out.print(n + " ");
			}	
		}
		//When the array is 3/4th full, the array size is doubled
		else if(length == array_3by4) {
			int newArray[] = new int[arraysize*2];

			for(int i = 0; i < Array.length; i++) {
				newArray[i] = Array[i];
			}
			arraysize = arraysize * 2;
			Array = newArray;

			for(int j : newArray) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
		else {
			System.out.println("ArrayIndexOutOfBoundsException");
		}
	}

	//This function returns the number of elements in the array
	public int size()
	{
		return arraysize;
	}

	//This function returns the string representation of the array
	public String toString()
	{
		String s = Arrays.toString(Array);
		return s;
	}

	public static void main(String[] args) {
		ArrayList obj = new ArrayList();
		obj.add(2);
		obj.add(7);
		obj.add(4);
		obj.add(6);
		obj.pop();
		obj.resize();
		System.out.println("toString: " +obj.toString());
		System.out.println("Size of new Array: " +obj.size());
	}
}