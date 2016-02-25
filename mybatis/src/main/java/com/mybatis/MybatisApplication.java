package com.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

@SpringBootApplication
public class MybatisApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MybatisApplication.class, args);
		Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sqlSessionFactory.openSession();

		//Create a new student object
		Student student = new Student("long", 18);

        //Insert student data
		session.insert("Student.insert", student);
		System.out.println("record inserted successfully");
        session.commit();

        //select contact all contacts
        List<Student> students = session.selectList("Student.getAll");
        for(Student st : students ){
            System.out.println(st.getId() + " " + st.getName() + " " + st.getAge());
        }
        session.commit();

        Student st = session.selectOne("Student.getById", student.getId());
        System.out.println(st.getId() + " " + st.getName() + " " + st.getAge());
        session.commit();

        st.setAge(20);
        session.update("Student.update", st);
        session.commit();

        Student st1 = session.selectOne("Student.getById", st.getId());
        System.out.println(st1.getId() + " " + st1.getName() + " " + st1.getAge());
        session.commit();

        session.delete("Student.deleteById", st1.getId());
        session.commit();

        session.close();
	}
}
