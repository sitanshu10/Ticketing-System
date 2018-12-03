import java.util.*;
import java.io.*;
public class movie
{
	static int readers,nwriter=0,mutex,wrt;
	static int seat[] = new int[10];
	static String codes;
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("seats.txt"));
		for(int i=0;i<10;i++)
		{
			seat[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		int choice = 0;
		Scanner sc = new Scanner(System.in);
		while(nwriter<=10)
		{
			System.out.println("Enter your choice:");
			System.out.println("1.Read");
			System.out.println("2.Write");
			choice = sc.nextInt();
			if(choice == 1)
			{
				read();
			}
			else if(choice == 2)
			{
				nwriter++;
				writes();
			}
			else
			{
				System.out.println("Invalid choice");
			}
		}
	}
	public static void writes() throws IOException
	{
		Scanner sc = new Scanner(System.in);
		waitwrt();
		int flag = 0;
		while(flag == 0)
		{
			System.out.println("Please enter seat number you want:");
			int n = sc.nextInt();
			if(seat[n]!=0)
			{
				System.out.println("Sorry. This seat is already booked.Please try again.");
			}
			else
			{
				System.out.println("This seat is available. Please enter the code given to you:");
				seat[n] = sc.nextInt();
				BufferedWriter writer = new BufferedWriter(new FileWriter("seats.txt"));
				String linesep = System.getProperty("line.separator");
				for(int i=0;i<10;i++)
				{
					writer.write(Integer.toString(seat[i]) + linesep);
				}
				writer.close();
				flag = 1;
			}
		}
		signalwrt();
		System.out.println();
	}
	public static void read() throws IOException
	{
		Scanner sc = new Scanner(System.in);
		waitmutex();
		System.out.println("MUTEX:"+mutex);
		BufferedReader br = new BufferedReader(new FileReader("sitanshu.txt"));
		codes = br.readLine();
		br.close();
		readers = codes.charAt(2)-'0';
		readers++;
		codes = codes.substring(0,2)+Integer.toString(readers);
		FileOutputStream fos = new FileOutputStream("sitanshu.txt");
		byte[] buffer = codes.getBytes();
		fos.write(buffer);
		fos.close();
		if(readers == 1)
			waitwrt();
		signalmutex();
		System.out.println("MUTEX:"+mutex);
		System.out.println("Arrangement of seats is as follows:");
		for(int i=0;i<10;i++)
		{
			System.out.print(seat[i]+"  ");
		}
		System.out.println();
		System.out.println("Please enter 'v' when you have finished viewing:");
		String v = sc.next();
		waitmutex();
		System.out.println("MUTEX:"+mutex);
		BufferedReader bre = new BufferedReader(new FileReader("Sitanshu.txt"));
		codes = bre.readLine();
		bre.close();
		readers = codes.charAt(2)-'0';
		readers--;
		codes = codes.substring(0,2)+Integer.toString(readers);
		FileOutputStream fose = new FileOutputStream("Sitanshu.txt");
		byte[] buffere = codes.getBytes();
		fose.write(buffere);
		fose.close();
		if(readers == 0)
			signalwrt();
		signalmutex();
		System.out.println("MUTEX:"+mutex);
		System.out.println();
	}
	public static void waitwrt() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("sitanshu.txt"));
		codes = br.readLine();
		br.close();
		wrt = codes.charAt(0)-'0';
		if(wrt<=0)
			System.out.println("Currently other user(s) is/are either booking or viewing the seats. Please try again after some time.");
		while(wrt<=0);
		wrt--;
		System.out.println("WRT:"+wrt);
		codes = Integer.toString(wrt)+codes.substring(1);
		FileOutputStream fos = new FileOutputStream(“sitanshu.txt");
		byte[] buffer = codes.getBytes();
		fos.write(buffer);
		fos.close();
	}
	public static void waitmutex() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("sitanshu.txt"));
		codes = br.readLine();
		br.close();
		mutex = codes.charAt(1)-'0';
		if(mutex<=0)
			System.out.println("Currently other user(s) is/are either booking or viewing the seats. Please try again after some time.");
		while(mutex<=0);
		mutex--;
		codes = codes.substring(0,1)+Integer.toString(mutex)+codes.substring(2);
		FileOutputStream fos = new FileOutputStream("sitanshu.txt");
		byte[] buffer = codes.getBytes();
		fos.write(buffer);
		fos.close();
	}
	public static void signalwrt() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("sitanshu.txt"));
		codes = br.readLine();
		br.close();
		wrt = codes.charAt(0)-'0';
		wrt++;
		System.out.println("WRT:"+wrt);
		codes = Integer.toString(wrt)+codes.substring(1);
		FileOutputStream fos = new FileOutputStream("sitanshu.txt");
		byte[] buffer = codes.getBytes();
		fos.write(buffer);
		fos.close();
	}
	public static void signalmutex() throws IOException
	{
		BufferedReader br =new BufferedReader(new FileReader("sitanshu.txt"));
		codes = br.readLine();
		br.close();
		mutex = codes.charAt(1)-'0';
		mutex++;
		codes = codes.substring(0,1)+Integer.toString(mutex)+codes.substring(2);
		FileOutputStream fos = new FileOutputStream("sitanshu.txt");
		byte[] buffer = codes.getBytes();
		fos.write(buffer);
		fos.close();
	}
}

tests.java
import java.io.*;

public class tests {
    public static void main(String [] args) throws IOException{

        int seat[] = new int[10];
        String fileName = "seats.txt";
        BufferedReader br = new BufferedReader(new FileReader("seats.txt"));
		for(int i=0;i<10;i++)
		{
			seat[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		for(int i=0;i<10;i++)
		{
			System.out.print(seat[i]+"  ");
			seat[i]++;
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("seats.txt"));
		String linesep = System.getProperty("line.separator");
		for(int i=0;i<10;i++)
		{
			writer.write(Integer.toString(seat[i]) + linesep);
		}
		writer.close();
    }
}
