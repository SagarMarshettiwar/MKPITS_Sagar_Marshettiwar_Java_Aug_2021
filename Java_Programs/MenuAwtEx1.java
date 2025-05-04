/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

/**
 *
 * @author SAGAR
 */
public class MenuAwtEx1 extends Frame{
    MenuAwtEx1(){
        MenuBar mb=new MenuBar();
        Menu menu=new Menu("File");
        
        MenuItem i1=new MenuItem("New");
        MenuItem i2=new MenuItem("Open");
        MenuItem i3=new MenuItem("Close");
                        Menu submenu=new Menu("Project");
        MenuItem i4=new MenuItem("Rename");
        MenuItem i5=new MenuItem("Delete");
        menu.add(i1);
        menu.add(i2);
        menu.add(i3);
        menu.add(submenu);
        submenu.add(i4);
        submenu.add(i5);
        mb.add(menu);
        Menu menu1=new Menu("Edit");
        
        MenuItem i11=new MenuItem("Cut");
        MenuItem i22=new MenuItem("Copy");
        MenuItem i33=new MenuItem("Paste");
        menu1.add(i11);
        menu1.add(i22);
        menu1.add(i33);
        mb.add(menu1);
        setMenuBar(mb);
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        MenuAwtEx1 a=new MenuAwtEx1();
    }
}