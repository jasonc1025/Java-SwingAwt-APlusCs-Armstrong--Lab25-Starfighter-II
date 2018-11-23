//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class AlienHorde
{
	private List<Alien> aliens;

	private int size;
	
	public AlienHorde(int size)
	{
		aliens = new ArrayList<Alien>();
		int x = 25;
		int y = 50;
		
		this.size = size;
		
		for( int i = 0; i < size; i++)
		{
			if( x > StarFighter.HEIGHT - 50)
			{
				x = 25;
				y += 75;
			}
			aliens.add( new Alien( (int)(Math.random()*100)+805, (int)(Math.random()*500), 35, 35, 5 ));
			x += 75;
		}
	}

	public void add(Alien al)
	{
	   aliens.add(al);
	}

	public List<Alien> getAliens()
	{
		return aliens;
	}
	
	public void draw( Graphics window )
	{
		for( Alien a : aliens)
			a.draw( window );
	}

	public void move()
	{
		while(aliens.size()<size)
		{
			add(new Alien( (int)(Math.random()*100)+805, (int)(Math.random()*500), 35, 35, 5));
		} 
		
		for(Alien al : aliens)
		{
			al.move("LEFT");
		}
		
		removeOutOFBound();
		
	}


	public void removeOutOFBound()
	{
		for (int i = aliens.size()-1; i >=0; i--) {
			if(aliens.get(i).getX()<-10)
			{
				aliens.remove(i);
			}
		}
	}

	public int removeDeadOnes(List<Ammo> shots)
	{
		int count = 0;
		for(int i = 0; i < shots.size(); i++)
		{
			Ammo am = shots.get(i);
		   for(int j=0; j<aliens.size();j++)
		   {
		   	Alien al = aliens.get(j);
			   if ((am.getX() >= al.getX() && am.getX() <= al.getX()+60) ||
			        (am.getX()+10 >= al.getX() && am.getX()+10 <= al.getX()+60))
			   	     if ((am.getY() >= al.getY() && am.getY() <= al.getY()+60) ||
			   	     	   (am.getY()+10 >= al.getY() && am.getY()+10 <= al.getY()+60))
			   	     	   {
			   		         aliens.remove(j);
			   		         shots.remove(i);
			   		         count++;
			   		         break;
			   	     	   }
		   	}
		}
		return count;
	}

	public String toString()
	{
		return "" + aliens;
	}
}
