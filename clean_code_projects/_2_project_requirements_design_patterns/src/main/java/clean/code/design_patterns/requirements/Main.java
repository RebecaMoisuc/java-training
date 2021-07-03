package com.company;

 interface Prototype {
      default Prototype getClone() {
         return null;
     }
 }


package com.company;

public class StudentInfo implements StudentInfo1, Prototype {
    private int id;
    private String name;
    private String address;


    public StudentInfo() {
        System.out.println("   Info about student ");
        System.out.println("---------------------------------------");
        System.out.println("id" + "\t" + "  name" + "\t" +" address");
    }

    public  StudentInfo(int id, String name, String address) {

        this();
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public void showInfo(){

        System.out.println(id+"\t"+ " " + name+"\t" + address);
    }

    @Override
    public Prototype getClone() {

        return new StudentInfo(id, name, address);
    }
}






package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        {
            BufferedReader buffer =new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter Student Id: ");
            int studId=Integer.parseInt(buffer.readLine());
            System.out.print("\n");

            System.out.print("Enter Student Name: ");
            String studName=buffer.readLine();
            System.out.print("\n");


            System.out.print("Enter Student Address: ");
            String studAddress=buffer.readLine();
            System.out.print("\n");

            StudentInfo s1=new StudentInfo(studId, studName, studAddress);

            s1.showInfo();
            System.out.println("\n");
            StudentInfo s2=(StudentInfo)s1.getClone();
            s2.showInfo();
        }
    }

}



