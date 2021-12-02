// DO NOT USE PACKAGE

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class StudentManagementImpl implements StudentManagement {
    
    ArrayList<Student> students;

    public StudentManagementImpl()
    {
        this.students = new ArrayList<>();
    }

    public StudentManagementImpl(String dataPath)
    {
        this.students = new ArrayList<>();
        readData(dataPath);
    }

    private void readData(String filePath)
    {
        try
        {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            String readLine = "";
            while ((readLine = br.readLine()) != null)
            {
                String[] data = readLine.split("\\s");
                String id = data[0];
                int j = 1;
                String name = "";
                while(!data[j].equals("female") && !data[j].equals("male")){
                    name += data[j];
                    j++;
                }
                String gender = data[j];
                double gpa = Double.parseDouble(data[j+1]);
                String major = data[j+2];
                Student student = new Student(id, name, gender, gpa, major);
                this.students.add(student);
            }
            br.close();
        } catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public int getNoOfStudent() throws RemoteException
    {
        int count = 0;
        for(Student student : students){
            count += 1;
        }
        return count;
    }

    @Override
    public int getNoOfStudent_byGender(String gender) throws RemoteException
    {
        int count = 0;
        for(Student student : students){
            if(student.getGender().equals(gender)){
                count += 1;
            }
        }
        return count;
    }

    @Override
    public int getNoOfStudent_byMajor(String major) throws RemoteException
    {
        int count = 0;
        for(Student student : students){
            if(student.getMajor().equals(major)){
                count += 1;
            }
        }
        return count;
    }

    @Override
    public int getNoOfStudent_byGPA(double gpa) throws RemoteException
    {
        int count = 0;
        for(Student student : students){
            if(student.getGpa() == gpa){
                count += 1;
            }
        }
        return count;
    }

    @Override
    public Student findStudent_byName(String name) throws RemoteException
    {
        for(Student student : students){
            if(student.getName().equals(name)){
                return student;
            }
        }
        return new Student();
    }

    @Override
    public Student findStudent_byID(String id) throws RemoteException
    {
        for(Student student : students){
            if(student.getId().equals(id)){
                return student;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Student> findStudent_byMajor(String major) throws RemoteException
    {
        ArrayList<Student> stds = new ArrayList<Student>();
        for(Student student : students){
            if(student.getMajor().equals(major)){
                stds.add(student);
            }
        }
        return stds;
    }
    
    public ArrayList<Student> findStudent_byGender(String gender) throws RemoteException
    {
        ArrayList<Student> stds = new ArrayList<Student>();
        for(Student student : students){
            if(student.getGender().equals(gender)){
                stds.add(student);
            }
        }
        return stds;
    }

    @Override
    public ArrayList<Student> findStudent_byGPA(double GPA) throws RemoteException
    {
        ArrayList<Student> stds = new ArrayList<Student>();
        for(Student student : students){
            if(student.getGpa() == GPA){
                stds.add(student);
            }
        }
        return stds;
    }

    @Override
    public double getAvgGPA() throws RemoteException
    {
        double sum = 0;
        for(Student student : students){
            sum += student.getGpa();
        }
        return sum / getNoOfStudent();
    }

    public Student getHighestGPA(ArrayList<Student> stds) throws RemoteException
    {
        Student stdTemp = null;
        double highestGpa = 0;
        int i = 0;
        for(Student student : stds){
            if(i == 0){
                stdTemp = new Student(student);
                highestGpa = stdTemp.getGpa();
                i = 1;
                continue;
            }
            if(student.getGpa() > highestGpa){
                highestGpa = student.getGpa();
                stdTemp = student;
            }
        }
        return stdTemp;
    }

    public Student getLowestGPA(ArrayList<Student> stds) throws RemoteException
    {
        Student stdTemp = null;
        int i = 0;
        double lowestGpa = 0;
        for(Student student : stds){
            if(i == 0){
                stdTemp = new Student(student);
                lowestGpa = stdTemp.getGpa();
                i = 1;
                continue;
            }
            if(student.getGpa() < lowestGpa){
                lowestGpa = student.getGpa();
                stdTemp = student;
            }
        }
        return stdTemp;
    }

    @Override
    public Student getHighestGPA_byGender(String gender) throws RemoteException
    {
        ArrayList<Student> stds = findStudent_byGender(gender);
        return getHighestGPA(stds);
    }

    @Override
    public Student getHighestGPA_byFName(String fname) throws RemoteException
    {
        ArrayList<Student> stds = new ArrayList<Student>();
        for(Student student : students){
            if(student.getName().split("\\s")[0].equals(fname)){
                stds.add(student);
            }
        }
        return getHighestGPA(stds);
    }

    @Override
    public Student getHighestGPA_byLName(String lname) throws RemoteException
    {
        ArrayList<Student> stds = new ArrayList<Student>();
        for(Student student : students){
            int i = student.getName().split("\\s").length - 1;
            if(student.getName().split("\\s")[i].equals(lname)){
                stds.add(student);
            }
        }
        return getHighestGPA(stds);
    }

    @Override
    public Student getHighestGPA_byMajor(String major) throws RemoteException
    {
        ArrayList<Student> stds = findStudent_byMajor(major);
        return getHighestGPA(stds);
    }

    @Override
    public Student getLowestGPA_byMajor(String major) throws RemoteException
    {
        ArrayList<Student> stds = findStudent_byMajor(major);
        return getLowestGPA(stds);
    }

    @Override
	public ArrayList<Student> getTop10_byGPA() throws RemoteException
	{
		// Insert your code here ...
        return null;
	}

	@Override
	public ArrayList<Student> getTop10GPA_byGender(String gender) throws RemoteException
	{
        if(students == null || students.size() == 0){
            return null;
        }
        // sort students
        ArrayList<Student> sortedArr;
        sortedArr = (ArrayList<Student>) students.clone();
        for(int i = 0; i < sortedArr.size() - 1; i++){
            for(int j =0; j<sortedArr.size() -i -1; j++){
                if(sortedArr.get(j).getGpa() < sortedArr.get(j+1).getGpa()){
                    Student temp = new Student();
                    temp.copy(sortedArr.get(j));
                    sortedArr.set(j, sortedArr.get(j+1));
                    sortedArr.set(j+1, temp);
                }
            }
        }

        ArrayList<Student> arrOutput = new ArrayList<>();
        if(sortedArr.size > 0){
            int count = 0;
            for(Student s: sortedArr){
                if(s.getGender().equalsIgnoreCase(gender) && count < 10){
                    count ++;
                    arrOutput.add(s);
                }
            }
        }
        return arrOutput;
	}

	@Override
	public ArrayList<Student> getTop10GPA_byMajor(String major) throws RemoteException
	{
		// Insert your code here ...
        if(students == null || students.size() == 0){
            return null;
        }
        // sort students
        ArrayList<Student> sortedArr;
        sortedArr = (ArrayList<Student>) students.clone();
        for(int i = 0; i < sortedArr.size() - 1; i++){
            for(int j =0; j<sortedArr.size() -i -1; j++){
                if(sortedArr.get(j).getGpa() < sortedArr.get(j+1).getGpa()){
                    Student temp = new Student();
                    temp.copy(sortedArr.get(j));
                    sortedArr.set(j, sortedArr.get(j+1));
                    sortedArr.set(j+1, temp);
                }
            }
        }

        ArrayList<Student> arrOutput = new ArrayList<>();
        if(sortedArr.size > 0){
            int count = 0;
            for(Student s: sortedArr){
                if(s.getMajor().equalsIgnoreCase(major) && count < 10){
                    count ++;
                    arrOutput.add(s);
                }
            }
        }
        return arrOutput;
	}

	@Override
	public ArrayList<Student> getLast10GPA_byGender(String gender) throws RemoteException
	{
		// Insert your code here ...
         if(students == null || students.size() == 0){
            return null;
        }
        // sort students
        ArrayList<Student> sortedArr;
        sortedArr = (ArrayList<Student>) students.clone();
        for(int i = 0; i < sortedArr.size() - 1; i++){
            for(int j =0; j<sortedArr.size() -i -1; j++){
                if(sortedArr.get(j).getGpa() > sortedArr.get(j+1).getGpa()){
                    Student temp = new Student();
                    temp.copy(sortedArr.get(j));
                    sortedArr.set(j, sortedArr.get(j+1));
                    sortedArr.set(j+1, temp);
                }
            }
        }

        ArrayList<Student> arrOutput = new ArrayList<>();
        if(sortedArr.size > 0){
            int count = 0;
            for(Student s: sortedArr){
                if(s.getGender().equalsIgnoreCase(gender) && count < 10){
                    count ++;
                    arrOutput.add(s);
                }
            }
        }
        return arrOutput;
	}

	@Override
	public ArrayList<Student> getLast10GPA_byMajor(String major) throws RemoteException
	{
		// Insert your code here ...
        if(students == null || students.size() == 0){
            return null;
        }
        // sort students
        ArrayList<Student> sortedArr;
        sortedArr = (ArrayList<Student>) students.clone();
        for(int i = 0; i < sortedArr.size() - 1; i++){
            for(int j =0; j<sortedArr.size() -i -1; j++){
                if(sortedArr.get(j).getGpa() > sortedArr.get(j+1).getGpa()){
                    Student temp = new Student();
                    temp.copy(sortedArr.get(j));
                    sortedArr.set(j, sortedArr.get(j+1));
                    sortedArr.set(j+1, temp);
                }
            }
        }

        ArrayList<Student> arrOutput = new ArrayList<>();
        if(sortedArr.size > 0){
            int count = 0;
            for(Student s: sortedArr){
                if(s.getMajor().equalsIgnoreCase(major) && count < 10){
                    count ++;
                    arrOutput.add(s);
                }
            }
        }
        return arrOutput;
	}
    
}
