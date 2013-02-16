package com.socialmarketing.commons.jsp;

public class JspUtils {
	private static final String[] javaKeywords = { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throws", "transient", "try", "void", "volatile", "while" };

	  public static final String makeJavaIdentifier(String paramString)
	  {
	    StringBuffer localStringBuffer = new StringBuffer(paramString.length());
	    if (!Character.isJavaIdentifierStart(paramString.charAt(0)))
	      localStringBuffer.append('_');
	    for (int i = 0; i < paramString.length(); i++)
	    {
	      char c = paramString.charAt(i);
	      if ((Character.isJavaIdentifierPart(c)) && (c != '_'))
	        localStringBuffer.append(c);
	      else if (c == '.')
	        localStringBuffer.append('_');
	      else
	        localStringBuffer.append(mangleChar(c));
	    }
	    if (isJavaKeyword(localStringBuffer.toString()))
	      localStringBuffer.append('_');
	    return localStringBuffer.toString();
	  }

	  public static final String mangleChar(char paramChar)
	  {
	    char[] arrayOfChar = new char[5];
	    arrayOfChar[0] = '_';
	    arrayOfChar[1] = Character.forDigit(paramChar >> '\f' & 0xF, 16);
	    arrayOfChar[2] = Character.forDigit(paramChar >> '\b' & 0xF, 16);
	    arrayOfChar[3] = Character.forDigit(paramChar >> '\004' & 0xF, 16);
	    arrayOfChar[4] = Character.forDigit(paramChar & 0xF, 16);
	    return new String(arrayOfChar);
	  }

	  public static boolean isJavaKeyword(String paramString)
	  {
	    int i = 0;
	    int j = javaKeywords.length;
	    while (i < j)
	    {
	      int k = (i + j) / 2;
	      int m = javaKeywords[k].compareTo(paramString);
	      if (m == 0)
	        return true;
	      if (m < 0)
	        i = k + 1;
	      else
	        j = k;
	    }
	    return false;
	  }
}
