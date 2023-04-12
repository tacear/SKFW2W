package mx.com.hunkabann.skf.util;

import java.beans.*;
import java.lang.reflect.*;


public class Snoopy 
{

	/**
	 * Obtiene el valor de las propiedades de un bean
	 * @param source	bean al cual se requiere saber sus propiedades
	 * @return	cadena con un listado de todas las propiedades del bean entrante
	 */
	public static String urga  ( Object source )
  	{
		PropertyDescriptor []  sourceProperties = null;
		try
		{
			sourceProperties = Introspector.getBeanInfo  ( source.getClass() ).getPropertyDescriptors() ;
			//System.out.println(sourceProperties);
		}
		catch( IntrospectionException ex )
		{
			return  ( "No encontró propiedades del objeto entrante" ) ;
		}


		StringBuffer results = new StringBuffer  () ;
		Object [  ]  value =  {  null  } ;
		//System.out.println("len: " + sourceProperties.length);


		for  ( int s = 1; s  <  sourceProperties.length; ++s )
		{
			String name = sourceProperties [ s ] .getName  (  ) ;
			//System.out.println("name: " + name);
			
			if(name.startsWith("tb"))
				continue;
			
			try
			{
				Method read = sourceProperties [ s ] .getReadMethod  (  ) ;

				value [ 0 ]  = read.invoke  ( source, null ) ;
				
				if( value [ 0 ]==null)
					 value [ 0 ]="";
				
				//System.out.println("value [ 0 ]: " + value [ 0 ]);
				results.append  ( ", " )
				.append  ( name )
				.append  ( " = " )
				.append  ( value [ 0 ]  );

			}
			catch  ( Exception ex )
			{
				results.append  ( "Exception transferring property " )
				.append  ( name )
				.append  ( ": " )
				.append  ( ex.getClass  (  ) .getName  (  )  )
				.append  ( "\r\n" ) ;
			}
		}

		return results.toString().substring(2) ;

	} 	//urga

}		// end of class