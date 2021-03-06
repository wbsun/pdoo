/**
  * Parses the string table
  */

package hu.uw.pallergabor.dedexer;
import java.io.IOException;

public class DexStringIdsBlock extends DexParser {

    public void parse() throws IOException {
        setDexOptimizationData( dexSignatureBlock.getDexOptimizationData() );
        int stringsSize = (int)dexPointerBlock.getStringIdsSize();
        file.seek( dexPointerBlock.getStringIdsOffset() );
        long stringsPos[] = new long[ stringsSize ];
// Read the string offsets first
        for( int i = 0 ; i < stringsSize ; ++i ) {
            stringsPos[i] = readFileOffset();
            dump( "string["+i+"]: at 0x"+dumpLong( stringsPos[i] ) );
        }
// Then use these pointers to read the strings themselves
        setDumpOff();
        strings = new String[ stringsSize ];
        for( int i = 0 ; i < strings.length ; ++i ) {
            file.seek( stringsPos[i] );
            strings[i] = readString();
            dump( "// string["+i+"]: "+strings[i] );
        }
        setDumpOn();
    }

    public int getStringsSize() {
        return (int)dexPointerBlock.getStringIdsSize();
    }

    public String getString( int idx ) {
        return strings[ idx ];
    }

    public void setDexPointerBlock( DexPointerBlock dexPointerBlock ) {
        this.dexPointerBlock = dexPointerBlock;
    }

    public void setDexSignatureBlock( DexSignatureBlock dexSignatureBlock ) {
        this.dexSignatureBlock = dexSignatureBlock;
    }

    public static String escapeString( String input ) {
	StringBuffer b = new StringBuffer();
	
	
	if(input.length() == 1){
		//System.out.println("input length is 1");
		char c = input.charAt(0);
		int ci = (int)c;
		if(ci == 32) {
			b.append("\\ "); 
			return new String( b );	
		}
		else if(ci == 38){
			b.append("\\&"); 
			return new String( b );	}
//		}else if(c == '\"') {
//			b.append("\\\""); 
//			return new String( b );	
//		}
		else if(c == 60) {
			b.append("\\<");

			return new String( b );	}
		else if(c == 62) {
			b.append("\\>"); 
			return new String( b );	}
		else if( ci == 0 ){
			b.append( "\\0" );
		return new String( b );	}
		else 
		if( ci == 7 ) {
			b.append( "\\a" );
		return new String( b );	}
		else 
		if( ci == 8 ){
			b.append( "\\b" );
		return new String( b );	}
		else
		if( ci == 9 ){
			b.append( "\\t" );
			return new String( b );	}
		else
		if( ci == 12 ) {
			b.append( "\\f" );
			return new String( b );	}
		else
		if( ci == 10 ) {
			b.append( "\\n" );
			return new String( b );	}
		else
		if( ci == 13 ) {
			b.append( "\\r" );
			return new String( b );	}
//		}else if( ci == 39) {
//			b.append("\\\'"); 
//			return new String( b );	
//		}
	}
	
	 
	for( int i = 0 ; i < input.length() ; ++i ) {
		char c = input.charAt( i );
		int ci = (int)c;
		if( c == '\\' )
			b.append( "\\\\" );
		else
		if( c == '\"' )
			b.append( "\\\"" );
		else
		if( ci >= 32 && ci <= 127 )
//			if(ci == 32) {
//			//	System.out.println("YAY GOT A SPACE!");
//				b.append("\\ ");
//			}
//			else
//				if(ci==38)
//					b.append("\\&");
//				else
//					if(ci == 60)
//						b.append("\\<");
//					else
//						if(ci==62)
//							b.append("\\>");
//			else 
				 
				b.append( c );
		else
		if( ci == 0 )
			b.append( "\\0" );
		else
		if( ci == 7 )
			b.append( "\\a" );
		else
		if( ci == 8 )
			b.append( "\\b" );
		else
		if( ci == 9 )
			b.append( "\\t" );
		else
		if( ci == 12 )
			b.append( "\\f" );
		else
		if( ci == 10 )
			b.append( "\\n" );
		else
		if( ci == 13 )
			b.append( "\\r" );
		else 
		{
		  	b.append( "\\u" );
		   	b.append( HEXCHAR.charAt( ( ci & 0xF000 ) >> 12 ) );
		   	b.append( HEXCHAR.charAt( ( ci & 0x0F00 ) >>  8 ) );
		   	b.append( HEXCHAR.charAt( ( ci & 0x00F0 ) >>  4 ) );
		   	b.append( HEXCHAR.charAt( ci & 0x000F ) );
		}
	}
	return new String( b );
    }

    private String              strings[] = null; 
    private DexPointerBlock     dexPointerBlock = null;
    private DexSignatureBlock   dexSignatureBlock;
    private static final String HEXCHAR = "0123456789ABCDEF";
    
}

