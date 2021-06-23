package it.uniroma2.dicii.isw2.jcs.paramTests;



import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.apache.jcs.JCS;
import org.apache.jcs.access.CacheAccess;
import org.apache.jcs.engine.behavior.ICompositeCacheAttributes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class JCSLightLoadUnitTest {
	
	private int items;
	private String confFile;
	private String istance;
	private CacheAccess jcs;
	
	@Parameters
	public static Collection<Object[]> getTestParameters(){
		
		return Arrays.asList(new Object[][] {
			
			{20000,"/TestSimpleLoad.ccf","testCache1"}
			
		});
		
	}
	
	
	public JCSLightLoadUnitTest (int items,String confFile,String istance) throws Exception {
		
		this.items = items;
		this.confFile = confFile;
		this.istance = istance;
	}
	
	@Before
    public void configure() throws Exception
    {
        JCS.setConfigFilename( this.confFile );
        this.jcs = JCS.getInstance( this.istance );        
        ICompositeCacheAttributes cattr = jcs.getCacheAttributes();
        cattr.setMaxObjects( items + 1 );
        this.jcs.setCacheAttributes( cattr );
    }
	

	
	@Test
	public void testSimpleLoad()
	        throws Exception
	    {
	     	
	        for ( int i = 1; i <= items; i++ )
	        {
	            jcs.put( i + ":key", "data" + i );
	        }
	        
	        for ( int i = items; i > 0; i-- )
	        {
	            String res = (String) jcs.get( i + ":key" );
	            if ( res == null )
	            {
	                assertNotNull( "[" + i + ":key] should not be null", res );
	            }
	        }

	        // test removal
	        jcs.remove( "300:key" );
	        assertNull( jcs.get( "300:key" ) );

	    }
	

	
}
