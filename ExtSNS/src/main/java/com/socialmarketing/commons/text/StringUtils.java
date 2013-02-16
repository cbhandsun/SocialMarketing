package com.socialmarketing.commons.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static String allowed = "-0123456789.";
	private static Random rn = new Random();

	public static int getIntegerNumber(String paramString) {
		return Integer.parseInt(getNumber(paramString));
	}

	public static double getDoubleNumber(String paramString) {
		return Double.parseDouble(getNumber(paramString));
	}

	public static String replace(String paramString1, String paramString2,
			String paramString3) {
		int i = 1;
		if ((paramString1 != null) && (paramString2 != null)
				&& (paramString2.length() > 0) && (paramString3 != null)) {
			StringBuffer localStringBuffer = null;
			int j = 0;
			do {
				int k = paramString1.indexOf(paramString2, j);
				if (k < 0)
					break;
				if (localStringBuffer == null)
					localStringBuffer = new StringBuffer();
				localStringBuffer.append(paramString1.substring(j, k));
				localStringBuffer.append(paramString3);
				k += paramString2.length();
				j = k;
			} while (i != 0);
			if (j == 0)
				return paramString1;
			localStringBuffer.append(paramString1.substring(j));
			return localStringBuffer.toString();
		}
		return paramString1;
	}

	public static String replaceReturns(String paramString1, String paramString2) {
		if (paramString1 == null)
			return null;
		String str = replace(paramString1, "\r\n", paramString2);
		str = replace(str, "\r", paramString2);
		str = replace(str, "\n", paramString2);
		return str;
	}

	public static String toString(String paramString) {
		if (paramString != null)
			return paramString;
		return "";
	}

	public static String toHtml(String paramString) {
		if (paramString != null) {
			if (paramString.trim().equals(""))
				return "&nbsp;";
			return toHtmlValue(paramString);
		}
		return "&nbsp;";
	}

	public static String toHtmlValue(String paramString) {
		return toHtmlValue(paramString, true, true);
	}

	public static String toHtmlValue(String paramString, boolean paramBoolean1,
			boolean paramBoolean2) {
		if (paramString != null) {
			String str = null;
			if (paramBoolean1)
				str = paramString.trim();
			else
				str = paramString;
			str = toBasicHtmlChars(str);
			str = replace(str, "\r\n", "\n");
			str = replace(str, "\n\r", "\n");
			str = replace(str, "\r", "\n");
			if (paramBoolean2)
				str = replace(str, "\n", "<br />");
			str = toHtmlChars(str);
			return str;
		}
		return "";
	}

	public static String toBasicHtmlChars(String paramString) {
		paramString = replace(paramString, "&", "&amp;");
		paramString = replace(paramString, "\"", "&quot;");
		paramString = replace(paramString, "<", "&lt;");
		paramString = replace(paramString, ">", "&gt;");
		paramString = replace(paramString, "//lt;", "<");
		paramString = replace(paramString, "//gt;", ">");
		paramString = replace(paramString, "&lt;br /&gt;", "<br />");
		return paramString;
	}

	public static String toHtmlChars(String paramString) {
		if (paramString != null) {
			paramString = replace(paramString, "¡", "&iexcl;");
			paramString = replace(paramString, "¢", "&cent;");
			paramString = replace(paramString, "£", "&pound;");
			paramString = replace(paramString, "¤", "&curren;");
			paramString = replace(paramString, "¥", "&yen;");
			paramString = replace(paramString, "¦", "&brvbar;");
			paramString = replace(paramString, "§", "&sect;");
			paramString = replace(paramString, "¨", "&uml;");
			paramString = replace(paramString, "©", "&copy;");
			paramString = replace(paramString, "ª", "&ordf;");
			paramString = replace(paramString, "«", "&laquo;");
			paramString = replace(paramString, "¬", "&not;");
			paramString = replace(paramString, "­", "&shy;");
			paramString = replace(paramString, "®", "&reg;");
			paramString = replace(paramString, "¯", "&macr;");
			paramString = replace(paramString, "°", "&deg;");
			paramString = replace(paramString, "±", "&plusmn;");
			paramString = replace(paramString, "²", "&sup2;");
			paramString = replace(paramString, "³", "&sup3;");
			paramString = replace(paramString, "´", "&acute;");
			paramString = replace(paramString, "µ", "&micro;");
			paramString = replace(paramString, "¶", "&para;");
			paramString = replace(paramString, "·", "&middot;");
			paramString = replace(paramString, "¸", "&cedil;");
			paramString = replace(paramString, "¹", "&sup1;");
			paramString = replace(paramString, "º", "&ordm;");
			paramString = replace(paramString, "»", "&raquo;");
			paramString = replace(paramString, "¼", "&frac14;");
			paramString = replace(paramString, "½", "&frac12;");
			paramString = replace(paramString, "¾", "&frac34;");
			paramString = replace(paramString, "¿", "&iquest;");
			paramString = replace(paramString, "À", "&Agrave;");
			paramString = replace(paramString, "Á", "&Aacute;");
			paramString = replace(paramString, "Â", "&Acirc;");
			paramString = replace(paramString, "Ã", "&Atilde;");
			paramString = replace(paramString, "Ä", "&Auml;");
			paramString = replace(paramString, "Å", "&Aring;");
			paramString = replace(paramString, "Æ", "&AElig;");
			paramString = replace(paramString, "Ç", "&Ccedil;");
			paramString = replace(paramString, "È", "&Egrave;");
			paramString = replace(paramString, "É", "&Eacute;");
			paramString = replace(paramString, "Ê", "&Ecirc;");
			paramString = replace(paramString, "Ë", "&Euml;");
			paramString = replace(paramString, "Ì", "&Igrave;");
			paramString = replace(paramString, "Í", "&Iacute;");
			paramString = replace(paramString, "Î", "&Icirc;");
			paramString = replace(paramString, "Ï", "&Iuml;");
			paramString = replace(paramString, "Ð", "&ETH;");
			paramString = replace(paramString, "Ñ", "&Ntilde;");
			paramString = replace(paramString, "Ò", "&Ograve;");
			paramString = replace(paramString, "Ó", "&Oacute;");
			paramString = replace(paramString, "Ô", "&Ocirc;");
			paramString = replace(paramString, "Õ", "&Otilde;");
			paramString = replace(paramString, "Ö", "&Ouml;");
			paramString = replace(paramString, "×", "&times;");
			paramString = replace(paramString, "Ø", "&Oslash;");
			paramString = replace(paramString, "Ù", "&Ugrave;");
			paramString = replace(paramString, "Ú", "&Uacute;");
			paramString = replace(paramString, "Û", "&Ucirc;");
			paramString = replace(paramString, "Ü", "&Uuml;");
			paramString = replace(paramString, "Ý", "&Yacute;");
			paramString = replace(paramString, "Þ", "&THORN;");
			paramString = replace(paramString, "ß", "&szlig;");
			paramString = replace(paramString, "à", "&agrave;");
			paramString = replace(paramString, "á", "&aacute;");
			paramString = replace(paramString, "â", "&acirc;");
			paramString = replace(paramString, "ã", "&atilde;");
			paramString = replace(paramString, "ä", "&auml;");
			paramString = replace(paramString, "å", "&aring;");
			paramString = replace(paramString, "æ", "&aelig;");
			paramString = replace(paramString, "ç", "&ccedil;");
			paramString = replace(paramString, "è", "&egrave;");
			paramString = replace(paramString, "é", "&eacute;");
			paramString = replace(paramString, "ê", "&ecirc;");
			paramString = replace(paramString, "ë", "&euml;");
			paramString = replace(paramString, "ì", "&igrave;");
			paramString = replace(paramString, "í", "&iacute;");
			paramString = replace(paramString, "î", "&icirc;");
			paramString = replace(paramString, "ï", "&iuml;");
			paramString = replace(paramString, "ð", "&eth;");
			paramString = replace(paramString, "ñ", "&ntilde;");
			paramString = replace(paramString, "ò", "&ograve;");
			paramString = replace(paramString, "ó", "&oacute;");
			paramString = replace(paramString, "ô", "&ocirc;");
			paramString = replace(paramString, "õ", "&otilde;");
			paramString = replace(paramString, "ö", "&ouml;");
			paramString = replace(paramString, "÷", "&divide;");
			paramString = replace(paramString, "ø", "&oslash;");
			paramString = replace(paramString, "ù", "&ugrave;");
			paramString = replace(paramString, "ú", "&uacute;");
			paramString = replace(paramString, "û", "&ucirc;");
			paramString = replace(paramString, "ü", "&uuml;");
			paramString = replace(paramString, "ý", "&yacute;");
			paramString = replace(paramString, "þ", "&thorn;");
			paramString = replace(paramString, "ÿ", "&yuml;");
			paramString = replace(paramString, "Œ", "&OElig;");
			paramString = replace(paramString, "œ", "&oelig;");
			paramString = replace(paramString, "Š", "&Scaron;");
			paramString = replace(paramString, "š", "&scaron;");
			paramString = replace(paramString, "Ÿ", "&Yuml;");
			paramString = replace(paramString, "ƒ", "&fnof;");
			paramString = replace(paramString, "ˆ", "&circ;");
			paramString = replace(paramString, "˜", "&tilde;");
			paramString = replace(paramString, "Α", "&Alpha;");
			paramString = replace(paramString, "Β", "&Beta;");
			paramString = replace(paramString, "Γ", "&Gamma;");
			paramString = replace(paramString, "Δ", "&Delta;");
			paramString = replace(paramString, "Ε", "&Epsilon;");
			paramString = replace(paramString, "Ζ", "&Zeta;");
			paramString = replace(paramString, "Η", "&Eta;");
			paramString = replace(paramString, "Θ", "&Theta;");
			paramString = replace(paramString, "Ι", "&Iota;");
			paramString = replace(paramString, "Κ", "&Kappa;");
			paramString = replace(paramString, "Λ", "&Lambda;");
			paramString = replace(paramString, "Μ", "&Mu;");
			paramString = replace(paramString, "Ν", "&Nu;");
			paramString = replace(paramString, "Ξ", "&Xi;");
			paramString = replace(paramString, "Ο", "&Omicron;");
			paramString = replace(paramString, "Π", "&Pi;");
			paramString = replace(paramString, "Ρ", "&Rho;");
			paramString = replace(paramString, "Σ", "&Sigma;");
			paramString = replace(paramString, "Τ", "&Tau;");
			paramString = replace(paramString, "Υ", "&Upsilon;");
			paramString = replace(paramString, "Φ", "&Phi;");
			paramString = replace(paramString, "Χ", "&Chi;");
			paramString = replace(paramString, "Ψ", "&Psi;");
			paramString = replace(paramString, "Ω", "&Omega;");
			paramString = replace(paramString, "α", "&alpha;");
			paramString = replace(paramString, "β", "&beta;");
			paramString = replace(paramString, "γ", "&gamma;");
			paramString = replace(paramString, "δ", "&delta;");
			paramString = replace(paramString, "ε", "&epsilon;");
			paramString = replace(paramString, "ζ", "&zeta;");
			paramString = replace(paramString, "η", "&eta;");
			paramString = replace(paramString, "θ", "&theta;");
			paramString = replace(paramString, "ι", "&iota;");
			paramString = replace(paramString, "κ", "&kappa;");
			paramString = replace(paramString, "λ", "&lambda;");
			paramString = replace(paramString, "μ", "&mu;");
			paramString = replace(paramString, "ν", "&nu;");
			paramString = replace(paramString, "ξ", "&xi;");
			paramString = replace(paramString, "ο", "&omicron;");
			paramString = replace(paramString, "π", "&pi;");
			paramString = replace(paramString, "ρ", "&rho;");
			paramString = replace(paramString, "σ", "&sigmaf;");
			paramString = replace(paramString, "σ", "&sigma;");
			paramString = replace(paramString, "τ", "&tau;");
			paramString = replace(paramString, "υ", "&upsilon;");
			paramString = replace(paramString, "φ", "&phi;");
			paramString = replace(paramString, "χ", "&chi;");
			paramString = replace(paramString, "ψ", "&psi;");
			paramString = replace(paramString, "ω", "&omega;");
			paramString = replace(paramString, "ϑ", "&thetasym;");
			paramString = replace(paramString, "ϒ", "&upsih;");
			paramString = replace(paramString, "ϖ", "&piv;");
			paramString = replace(paramString, " ", "&ensp;");
			paramString = replace(paramString, " ", "&emsp;");
			paramString = replace(paramString, " ", "&thinsp;");
			paramString = replace(paramString, "‌", "&zwnj;");
			paramString = replace(paramString, "‍", "&zwj;");
			paramString = replace(paramString, "‎", "&lrm;");
			paramString = replace(paramString, "‏", "&rlm;");
			paramString = replace(paramString, "–", "&ndash;");
			paramString = replace(paramString, "—", "&mdash;");
			paramString = replace(paramString, "‘", "&lsquo;");
			paramString = replace(paramString, "’", "&rsquo;");
			paramString = replace(paramString, "‚", "&sbquo;");
			paramString = replace(paramString, "“", "&ldquo;");
			paramString = replace(paramString, "”", "&rdquo;");
			paramString = replace(paramString, "„", "&bdquo;");
			paramString = replace(paramString, "†", "&dagger;");
			paramString = replace(paramString, "‡", "&Dagger;");
			paramString = replace(paramString, "•", "&bull;");
			paramString = replace(paramString, "…", "&hellip;");
			paramString = replace(paramString, "‰", "&permil;");
			paramString = replace(paramString, "′", "&prime;");
			paramString = replace(paramString, "″", "&Prime;");
			paramString = replace(paramString, "‹", "&lsaquo;");
			paramString = replace(paramString, "›", "&rsaquo;");
			paramString = replace(paramString, "‾", "&oline;");
			paramString = replace(paramString, "⁄", "&frasl;");
			paramString = replace(paramString, "€", "&euro;");
			paramString = replace(paramString, "ℑ", "&image;");
			paramString = replace(paramString, "℘", "&weierp;");
			paramString = replace(paramString, "ℜ", "&real;");
			paramString = replace(paramString, "™", "&trade;");
			paramString = replace(paramString, "ℵ", "&alefsym;");
			paramString = replace(paramString, "←", "&larr;");
			paramString = replace(paramString, "↑", "&uarr;");
			paramString = replace(paramString, "→", "&rarr;");
			paramString = replace(paramString, "↓", "&darr;");
			paramString = replace(paramString, "↔", "&harr;");
			paramString = replace(paramString, "↵", "&crarr;");
			paramString = replace(paramString, "⇐", "&lArr;");
			paramString = replace(paramString, "⇑", "&uArr;");
			paramString = replace(paramString, "⇒", "&rArr;");
			paramString = replace(paramString, "⇓", "&dArr;");
			paramString = replace(paramString, "⇔", "&hArr;");
			paramString = replace(paramString, "∀", "&forall;");
			paramString = replace(paramString, "∂", "&part;");
			paramString = replace(paramString, "∃", "&exist;");
			paramString = replace(paramString, "∅", "&empty;");
			paramString = replace(paramString, "∇", "&nabla;");
			paramString = replace(paramString, "∈", "&isin;");
			paramString = replace(paramString, "∉", "&notin;");
			paramString = replace(paramString, "∋", "&ni;");
			paramString = replace(paramString, "∏", "&prod;");
			paramString = replace(paramString, "∑", "&sum;");
			paramString = replace(paramString, "−", "&minus;");
			paramString = replace(paramString, "∗", "&lowast;");
			paramString = replace(paramString, "√", "&radic;");
			paramString = replace(paramString, "∝", "&prop;");
			paramString = replace(paramString, "∞", "&infin;");
			paramString = replace(paramString, "∠", "&ang;");
			paramString = replace(paramString, "∧", "&and;");
			paramString = replace(paramString, "∨", "&or;");
			paramString = replace(paramString, "∩", "&cap;");
			paramString = replace(paramString, "∪", "&cup;");
			paramString = replace(paramString, "∫", "&int;");
			paramString = replace(paramString, "∴", "&there4;");
			paramString = replace(paramString, "∼", "&sim;");
			paramString = replace(paramString, "≅", "&cong;");
			paramString = replace(paramString, "≈", "&asymp;");
			paramString = replace(paramString, "≠", "&ne;");
			paramString = replace(paramString, "≡", "&equiv;");
			paramString = replace(paramString, "≤", "&le;");
			paramString = replace(paramString, "≥", "&ge;");
			paramString = replace(paramString, "⊂", "&sub;");
			paramString = replace(paramString, "⊃", "&sup;");
			paramString = replace(paramString, "⊄", "&nsub;");
			paramString = replace(paramString, "⊆", "&sube;");
			paramString = replace(paramString, "⊇", "&supe;");
			paramString = replace(paramString, "⊕", "&oplus;");
			paramString = replace(paramString, "⊗", "&otimes;");
			paramString = replace(paramString, "⊥", "&perp;");
			paramString = replace(paramString, "⋅", "&sdot;");
			paramString = replace(paramString, "⌈", "&lceil;");
			paramString = replace(paramString, "⌉", "&rceil;");
			paramString = replace(paramString, "⌊", "&lfloor;");
			paramString = replace(paramString, "⌋", "&rfloor;");
			paramString = replace(paramString, "〈", "&lang;");
			paramString = replace(paramString, "〉", "&rang;");
			paramString = replace(paramString, "◊", "&loz;");
			paramString = replace(paramString, "♠", "&spades;");
			paramString = replace(paramString, "♣", "&clubs;");
			paramString = replace(paramString, "♥", "&hearts;");
			paramString = replace(paramString, "♦", "&diams;");
			paramString = replace(paramString, "&apos;", "'");
			return paramString;
		}
		return "";
	}

	public static String toPseudoHtmlValue(String paramString) {
		String str = toHtmlValue(paramString);
		str = replace(str, "<br>", "\n");
		str = replace(str, "<br />", "\n");
		str = replace(str, "<br/>", "\n");
		return str;
	}

	public static String fromHtmlValue(String paramString) {
		if (paramString != null) {
			String str = paramString;
			str = replace(str, "&quot;", "\"");
			str = replace(str, "&apos;", "'");
			str = replace(str, "&lt;", "<");
			str = replace(str, "&gt;", ">");
			str = replace(str, "&nbsp;", " ");
			str = replace(str, "<br/>", "<br />");
			str = replace(str, "<br>", "<br />");
			str = replace(str, "<br />\n", "<br />");
			str = replace(str, "<br />", "\n");
			str = replace(str, "&amp;", "&");
			str = replace(str, "\r\n", "\n");
			str = replace(str, "\n\r", "\n");
			str = replace(str, "\r", "\n");
			str = fromHtmlChars(str);
			return str;
		}
		return "";
	}

	public static String fromHtmlChars(String paramString) {
		if (paramString != null) {
			paramString = replace(paramString, "&iexcl;", "¡");
			paramString = replace(paramString, "&cent;", "¢");
			paramString = replace(paramString, "&pound;", "£");
			paramString = replace(paramString, "&curren;", "¤");
			paramString = replace(paramString, "&yen;", "¥");
			paramString = replace(paramString, "&brvbar;", "¦");
			paramString = replace(paramString, "&sect;", "§");
			paramString = replace(paramString, "&uml;", "¨");
			paramString = replace(paramString, "&copy;", "©");
			paramString = replace(paramString, "&ordf;", "ª");
			paramString = replace(paramString, "&laquo;", "«");
			paramString = replace(paramString, "&not;", "¬");
			paramString = replace(paramString, "&shy;", "­");
			paramString = replace(paramString, "&reg;", "®");
			paramString = replace(paramString, "&macr;", "¯");
			paramString = replace(paramString, "&deg;", "°");
			paramString = replace(paramString, "&plusmn;", "±");
			paramString = replace(paramString, "&sup2;", "²");
			paramString = replace(paramString, "&sup3;", "³");
			paramString = replace(paramString, "&acute;", "´");
			paramString = replace(paramString, "&micro;", "µ");
			paramString = replace(paramString, "&para;", "¶");
			paramString = replace(paramString, "&middot;", "·");
			paramString = replace(paramString, "&cedil;", "¸");
			paramString = replace(paramString, "&sup1;", "¹");
			paramString = replace(paramString, "&ordm;", "º");
			paramString = replace(paramString, "&raquo;", "»");
			paramString = replace(paramString, "&frac14;", "¼");
			paramString = replace(paramString, "&frac12;", "½");
			paramString = replace(paramString, "&frac34;", "¾");
			paramString = replace(paramString, "&iquest;", "¿");
			paramString = replace(paramString, "&Agrave;", "À");
			paramString = replace(paramString, "&Aacute;", "Á");
			paramString = replace(paramString, "&Acirc;", "Â");
			paramString = replace(paramString, "&Atilde;", "Ã");
			paramString = replace(paramString, "&Auml;", "Ä");
			paramString = replace(paramString, "&Aring;", "Å");
			paramString = replace(paramString, "&AElig;", "Æ");
			paramString = replace(paramString, "&Ccedil;", "Ç");
			paramString = replace(paramString, "&Egrave;", "È");
			paramString = replace(paramString, "&Eacute;", "É");
			paramString = replace(paramString, "&Ecirc;", "Ê");
			paramString = replace(paramString, "&Euml;", "Ë");
			paramString = replace(paramString, "&Igrave;", "Ì");
			paramString = replace(paramString, "&Iacute;", "Í");
			paramString = replace(paramString, "&Icirc;", "Î");
			paramString = replace(paramString, "&Iuml;", "Ï");
			paramString = replace(paramString, "&ETH;", "Ð");
			paramString = replace(paramString, "&Ntilde;", "Ñ");
			paramString = replace(paramString, "&Ograve;", "Ò");
			paramString = replace(paramString, "&Oacute;", "Ó");
			paramString = replace(paramString, "&Ocirc;", "Ô");
			paramString = replace(paramString, "&Otilde;", "Õ");
			paramString = replace(paramString, "&Ouml;", "Ö");
			paramString = replace(paramString, "&times;", "×");
			paramString = replace(paramString, "&Oslash;", "Ø");
			paramString = replace(paramString, "&Ugrave;", "Ù");
			paramString = replace(paramString, "&Uacute;", "Ú");
			paramString = replace(paramString, "&Ucirc;", "Û");
			paramString = replace(paramString, "&Uuml;", "Ü");
			paramString = replace(paramString, "&Yacute;", "Ý");
			paramString = replace(paramString, "&THORN;", "Þ");
			paramString = replace(paramString, "&szlig;", "ß");
			paramString = replace(paramString, "&agrave;", "à");
			paramString = replace(paramString, "&aacute;", "á");
			paramString = replace(paramString, "&acirc;", "â");
			paramString = replace(paramString, "&atilde;", "ã");
			paramString = replace(paramString, "&auml;", "ä");
			paramString = replace(paramString, "&aring;", "å");
			paramString = replace(paramString, "&aelig;", "æ");
			paramString = replace(paramString, "&ccedil;", "ç");
			paramString = replace(paramString, "&egrave;", "è");
			paramString = replace(paramString, "&eacute;", "é");
			paramString = replace(paramString, "&ecirc;", "ê");
			paramString = replace(paramString, "&euml;", "ë");
			paramString = replace(paramString, "&igrave;", "ì");
			paramString = replace(paramString, "&iacute;", "í");
			paramString = replace(paramString, "&icirc;", "î");
			paramString = replace(paramString, "&iuml;", "ï");
			paramString = replace(paramString, "&eth;", "ð");
			paramString = replace(paramString, "&ntilde;", "ñ");
			paramString = replace(paramString, "&ograve;", "ò");
			paramString = replace(paramString, "&oacute;", "ó");
			paramString = replace(paramString, "&ocirc;", "ô");
			paramString = replace(paramString, "&otilde;", "õ");
			paramString = replace(paramString, "&ouml;", "ö");
			paramString = replace(paramString, "&divide;", "÷");
			paramString = replace(paramString, "&oslash;", "ø");
			paramString = replace(paramString, "&ugrave;", "ù");
			paramString = replace(paramString, "&uacute;", "ú");
			paramString = replace(paramString, "&ucirc;", "û");
			paramString = replace(paramString, "&uuml;", "ü");
			paramString = replace(paramString, "&yacute;", "ý");
			paramString = replace(paramString, "&thorn;", "þ");
			paramString = replace(paramString, "&yuml;", "ÿ");
			paramString = replace(paramString, "&OElig;", "Œ");
			paramString = replace(paramString, "&oelig;", "œ");
			paramString = replace(paramString, "&Scaron;", "Š");
			paramString = replace(paramString, "&scaron;", "š");
			paramString = replace(paramString, "&Yuml;", "Ÿ");
			paramString = replace(paramString, "&fnof;", "ƒ");
			paramString = replace(paramString, "&circ;", "ˆ");
			paramString = replace(paramString, "&tilde;", "˜");
			paramString = replace(paramString, "&Alpha;", "Α");
			paramString = replace(paramString, "&Beta;", "Β");
			paramString = replace(paramString, "&Gamma;", "Γ");
			paramString = replace(paramString, "&Delta;", "Δ");
			paramString = replace(paramString, "&Epsilon;", "Ε");
			paramString = replace(paramString, "&Zeta;", "Ζ");
			paramString = replace(paramString, "&Eta;", "Η");
			paramString = replace(paramString, "&Theta;", "Θ");
			paramString = replace(paramString, "&Iota;", "Ι");
			paramString = replace(paramString, "&Kappa;", "Κ");
			paramString = replace(paramString, "&Lambda;", "Λ");
			paramString = replace(paramString, "&Mu;", "Μ");
			paramString = replace(paramString, "&Nu;", "Ν");
			paramString = replace(paramString, "&Xi;", "Ξ");
			paramString = replace(paramString, "&Omicron;", "Ο");
			paramString = replace(paramString, "&Pi;", "Π");
			paramString = replace(paramString, "&Rho;", "Ρ");
			paramString = replace(paramString, "&Sigma;", "Σ");
			paramString = replace(paramString, "&Tau;", "Τ");
			paramString = replace(paramString, "&Upsilon;", "Υ");
			paramString = replace(paramString, "&Phi;", "Φ");
			paramString = replace(paramString, "&Chi;", "Χ");
			paramString = replace(paramString, "&Psi;", "Ψ");
			paramString = replace(paramString, "&Omega;", "Ω");
			paramString = replace(paramString, "&alpha;", "α");
			paramString = replace(paramString, "&beta;", "β");
			paramString = replace(paramString, "&gamma;", "γ");
			paramString = replace(paramString, "&delta;", "δ");
			paramString = replace(paramString, "&epsilon;", "ε");
			paramString = replace(paramString, "&zeta;", "ζ");
			paramString = replace(paramString, "&eta;", "η");
			paramString = replace(paramString, "&theta;", "θ");
			paramString = replace(paramString, "&iota;", "ι");
			paramString = replace(paramString, "&kappa;", "κ");
			paramString = replace(paramString, "&lambda;", "λ");
			paramString = replace(paramString, "&mu;", "μ");
			paramString = replace(paramString, "&nu;", "ν");
			paramString = replace(paramString, "&xi;", "ξ");
			paramString = replace(paramString, "&omicron;", "ο");
			paramString = replace(paramString, "&pi;", "π");
			paramString = replace(paramString, "&rho;", "ρ");
			paramString = replace(paramString, "&sigmaf;", "σ");
			paramString = replace(paramString, "&sigma;", "σ");
			paramString = replace(paramString, "&tau;", "τ");
			paramString = replace(paramString, "&upsilon;", "υ");
			paramString = replace(paramString, "&phi;", "φ");
			paramString = replace(paramString, "&chi;", "χ");
			paramString = replace(paramString, "&psi;", "ψ");
			paramString = replace(paramString, "&omega;", "ω");
			paramString = replace(paramString, "&thetasym;", "ϑ");
			paramString = replace(paramString, "&upsih;", "ϒ");
			paramString = replace(paramString, "&piv;", "ϖ");
			paramString = replace(paramString, "&ensp;", " ");
			paramString = replace(paramString, "&emsp;", " ");
			paramString = replace(paramString, "&thinsp;", " ");
			paramString = replace(paramString, "&zwnj;", "‌");
			paramString = replace(paramString, "&zwj;", "‍");
			paramString = replace(paramString, "&lrm;", "‎");
			paramString = replace(paramString, "&rlm;", "‏");
			paramString = replace(paramString, "&ndash;", "–");
			paramString = replace(paramString, "&mdash;", "—");
			paramString = replace(paramString, "&lsquo;", "‘");
			paramString = replace(paramString, "&rsquo;", "’");
			paramString = replace(paramString, "&sbquo;", "‚");
			paramString = replace(paramString, "&ldquo;", "“");
			paramString = replace(paramString, "&rdquo;", "”");
			paramString = replace(paramString, "&bdquo;", "„");
			paramString = replace(paramString, "&dagger;", "†");
			paramString = replace(paramString, "&Dagger;", "‡");
			paramString = replace(paramString, "&bull;", "•");
			paramString = replace(paramString, "&hellip;", "…");
			paramString = replace(paramString, "&permil;", "‰");
			paramString = replace(paramString, "&prime;", "′");
			paramString = replace(paramString, "&Prime;", "″");
			paramString = replace(paramString, "&lsaquo;", "‹");
			paramString = replace(paramString, "&rsaquo;", "›");
			paramString = replace(paramString, "&oline;", "‾");
			paramString = replace(paramString, "&frasl;", "⁄");
			paramString = replace(paramString, "&euro;", "€");
			paramString = replace(paramString, "&image;", "ℑ");
			paramString = replace(paramString, "&weierp;", "℘");
			paramString = replace(paramString, "&real;", "ℜ");
			paramString = replace(paramString, "&trade;", "™");
			paramString = replace(paramString, "&alefsym;", "ℵ");
			paramString = replace(paramString, "&larr;", "←");
			paramString = replace(paramString, "&uarr;", "↑");
			paramString = replace(paramString, "&rarr;", "→");
			paramString = replace(paramString, "&darr;", "↓");
			paramString = replace(paramString, "&harr;", "↔");
			paramString = replace(paramString, "&crarr;", "↵");
			paramString = replace(paramString, "&lArr;", "⇐");
			paramString = replace(paramString, "&uArr;", "⇑");
			paramString = replace(paramString, "&rArr;", "⇒");
			paramString = replace(paramString, "&dArr;", "⇓");
			paramString = replace(paramString, "&hArr;", "⇔");
			paramString = replace(paramString, "&forall;", "∀");
			paramString = replace(paramString, "&part;", "∂");
			paramString = replace(paramString, "&exist;", "∃");
			paramString = replace(paramString, "&empty;", "∅");
			paramString = replace(paramString, "&nabla;", "∇");
			paramString = replace(paramString, "&isin;", "∈");
			paramString = replace(paramString, "&notin;", "∉");
			paramString = replace(paramString, "&ni;", "∋");
			paramString = replace(paramString, "&prod;", "∏");
			paramString = replace(paramString, "&sum;", "∑");
			paramString = replace(paramString, "&minus;", "−");
			paramString = replace(paramString, "&lowast;", "∗");
			paramString = replace(paramString, "&radic;", "√");
			paramString = replace(paramString, "&prop;", "∝");
			paramString = replace(paramString, "&infin;", "∞");
			paramString = replace(paramString, "&ang;", "∠");
			paramString = replace(paramString, "&and;", "∧");
			paramString = replace(paramString, "&or;", "∨");
			paramString = replace(paramString, "&cap;", "∩");
			paramString = replace(paramString, "&cup;", "∪");
			paramString = replace(paramString, "&int;", "∫");
			paramString = replace(paramString, "&there4;", "∴");
			paramString = replace(paramString, "&sim;", "∼");
			paramString = replace(paramString, "&cong;", "≅");
			paramString = replace(paramString, "&asymp;", "≈");
			paramString = replace(paramString, "&ne;", "≠");
			paramString = replace(paramString, "&equiv;", "≡");
			paramString = replace(paramString, "&le;", "≤");
			paramString = replace(paramString, "&ge;", "≥");
			paramString = replace(paramString, "&sub;", "⊂");
			paramString = replace(paramString, "&sup;", "⊃");
			paramString = replace(paramString, "&nsub;", "⊄");
			paramString = replace(paramString, "&sube;", "⊆");
			paramString = replace(paramString, "&supe;", "⊇");
			paramString = replace(paramString, "&oplus;", "⊕");
			paramString = replace(paramString, "&otimes;", "⊗");
			paramString = replace(paramString, "&perp;", "⊥");
			paramString = replace(paramString, "&sdot;", "⋅");
			paramString = replace(paramString, "&lceil;", "⌈");
			paramString = replace(paramString, "&rceil;", "⌉");
			paramString = replace(paramString, "&lfloor;", "⌊");
			paramString = replace(paramString, "&rfloor;", "⌋");
			paramString = replace(paramString, "&lang;", "〈");
			paramString = replace(paramString, "&rang;", "〉");
			paramString = replace(paramString, "&loz;", "◊");
			paramString = replace(paramString, "&spades;", "♠");
			paramString = replace(paramString, "&clubs;", "♣");
			paramString = replace(paramString, "&hearts;", "♥");
			paramString = replace(paramString, "&diams;", "♦");
			return paramString;
		}
		return "";
	}

	public static String toHtmlText(String paramString) {
		paramString = replace(paramString, "<br />\r\n", "<br />");
		paramString = replace(paramString, "<br>\r\n", "<br />");
		paramString = replace(paramString, "\r\n", "<br />");
		paramString = replace(paramString, "<br />\n", "<br />");
		paramString = replace(paramString, "<br>\n", "<br />");
		paramString = replace(paramString, "\n", "<br />");
		return paramString;
	}

	public static String toHtmlTextValue(String paramString) {
		paramString = replace(paramString, "<br>\r\n", "\n");
		paramString = replace(paramString, "<br />\r\n", "\n");
		paramString = replace(paramString, "<br>", "\n");
		paramString = replace(paramString, "<br />", "\n");
		paramString = replace(paramString, "<br>\n", "\n");
		paramString = replace(paramString, "<br />\n", "\n");
		return paramString;
	}

	public static String toHtmlTextBlank(String paramString) {
		String str = replace(paramString, "<br>", "");
		str = replace(str, "<br />", "");
		return str;
	}

	public static String toDateTimeString(Timestamp paramTimestamp) {
		try {
			return DateFormat.getDateTimeInstance(3, 1).format(paramTimestamp);
		} catch (NullPointerException localNullPointerException) {
		}
		return "";
	}

	public static String toDateTimeString(java.util.Date paramDate) {
		try {
			return DateFormat.getDateTimeInstance(3, 1).format(paramDate);
		} catch (NullPointerException localNullPointerException) {
		}
		return "";
	}

	public static String toDateString(Timestamp paramTimestamp) {
		try {
			return DateFormat.getDateInstance(3).format(paramTimestamp);
		} catch (NullPointerException localNullPointerException) {
		}
		return "";
	}

	public static String toDateString(java.sql.Date paramDate) {
		try {
			return DateFormat.getDateInstance(3).format(paramDate);
		} catch (NullPointerException localNullPointerException) {
		}
		return "";
	}

	public static String toLongDateString(java.util.Date paramDate) {
		try {
			return DateFormat.getDateInstance(1).format(paramDate);
		} catch (NullPointerException localNullPointerException) {
		}
		return "";
	}

	public static String toFullDateString(java.util.Date paramDate) {
		try {
			return DateFormat.getDateInstance(0).format(paramDate);
		} catch (NullPointerException localNullPointerException) {
		}
		return "";
	}

	public static boolean hasText(String paramString) {
		return (paramString != null) && (!"".equals(paramString.trim()))
				&& (!"null".equals(paramString));
	}

	public static boolean isTrue(String paramString) {
		return (paramString != null)
				&& ("true".equals(paramString.trim().toLowerCase()));
	}

	private static String getNumber(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramString.length(); i++)
			if (allowed.indexOf(paramString.charAt(i)) > -1)
				localStringBuffer.append(paramString.charAt(i));
		return localStringBuffer.toString();
	}

	public static String getNumbersOnly(String paramString) {
		String str = "0123456789";
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramString.length(); i++)
			if (str.indexOf(paramString.charAt(i)) > -1)
				localStringBuffer.append(paramString.charAt(i));
		return localStringBuffer.toString();
	}

	public static String loadText(File paramFile) throws IOException {
		String str1 = System.getProperty("line.separator");
		StringBuffer localStringBuffer = new StringBuffer();
		BufferedReader localBufferedReader = new BufferedReader(new FileReader(
				paramFile));
		String str2 = null;
		for (int i = 0; (str2 = localBufferedReader.readLine()) != null; i = 1) {
			if (i != 0)
				localStringBuffer.append(str1);
			localStringBuffer.append(str2);
		}
		localBufferedReader.close();
		return localStringBuffer.toString();
	}

	public static String loadText(String paramString) throws IOException {
		String str1 = System.getProperty("line.separator");
		StringBuffer localStringBuffer = new StringBuffer();
		BufferedReader localBufferedReader = new BufferedReader(new FileReader(
				paramString));
		String str2 = null;
		for (int i = 0; (str2 = localBufferedReader.readLine()) != null; i = 1) {
			if (i != 0)
				localStringBuffer.append(str1);
			localStringBuffer.append(str2);
		}
		localBufferedReader.close();
		return localStringBuffer.toString();
	}

	public static String loadText(InputStream paramInputStream)
			throws IOException {
		StringBuffer localStringBuffer = new StringBuffer();
		byte[] arrayOfByte = new byte[1];
		while (paramInputStream.read(arrayOfByte) != -1)
			localStringBuffer.append(new String(arrayOfByte));
		paramInputStream.close();
		return localStringBuffer.toString();
	}

	public static void loadText(String paramString,
			ArrayList<String> paramArrayList) throws IOException {
		loadText(paramString, paramArrayList, false);
	}

	public static void loadText(String paramString,
			ArrayList<String> paramArrayList, boolean paramBoolean)
			throws IOException {
		BufferedReader localBufferedReader = new BufferedReader(new FileReader(
				paramString));
		String str = null;
		while ((str = localBufferedReader.readLine()) != null)
			if ((!paramBoolean)
					|| ((!str.startsWith("#")) && (!"".equals(str.trim()))))
				paramArrayList.add(str);
		localBufferedReader.close();
	}

	public static void loadText(InputStream paramInputStream,
			ArrayList<String> paramArrayList, boolean paramBoolean)
			throws IOException {
		BufferedReader localBufferedReader = new BufferedReader(
				new InputStreamReader(paramInputStream));
		String str = null;
		while ((str = localBufferedReader.readLine()) != null)
			if ((!paramBoolean)
					|| ((!str.startsWith("#")) && (!"".equals(str.trim()))))
				paramArrayList.add(str);
		localBufferedReader.close();
	}

	public static void saveText(String paramString1, String paramString2)
			throws IOException {
		saveText(new File(paramString1), paramString2);
	}

	public static void saveText(File paramFile, String paramString)
			throws IOException {
		BufferedWriter localBufferedWriter = new BufferedWriter(new FileWriter(
				paramFile));
		localBufferedWriter.write(paramString);
		localBufferedWriter.close();
	}

	public static String addS(long paramLong) {
		if (paramLong == 1L)
			return "";
		return "s";
	}

	public static String addES(long paramLong) {
		if (paramLong == 1L)
			return "";
		return "es";
	}

	public static int parseInt(String paramString, int paramInt) {
		try {
			return Integer.parseInt(paramString);
		} catch (Exception localException) {
		}
		return paramInt;
	}

	public static double parseDouble(String paramString, double paramDouble) {
		try {
			return Double.parseDouble(paramString);
		} catch (Exception localException) {
		}
		return paramDouble;
	}

	public static ArrayList parseExcelCSVLine(String paramString) {
		if (paramString == null)
			return null;
		ArrayList localArrayList = new ArrayList();
		int i = 0;
		int j = 0;
		StringBuffer localStringBuffer = new StringBuffer("");
		for (int k = 0; k < paramString.length(); k++) {
			char c = paramString.charAt(k);
			if (c == ',') {
				if (i == 0)
					j = 1;
				else
					localStringBuffer.append(c);
			} else if (c == '"') {
				if (i != 0)
					i = 0;
				else
					i = 1;
			} else
				localStringBuffer.append(c);
			if (k == paramString.length() - 1)
				j = 1;
			if (j != 0) {
				localArrayList.add(localStringBuffer.toString());
				localStringBuffer = new StringBuffer("");
				i = 0;
				j = 0;
			}
		}
		return localArrayList;
	}

	public static ArrayList toArrayList(String paramString1, String paramString2) {
		ArrayList localArrayList = null;
		StringTokenizer localStringTokenizer = new StringTokenizer(
				paramString1, paramString2);
		while (localStringTokenizer.hasMoreTokens()) {
			if (localArrayList == null)
				localArrayList = new ArrayList();
			localArrayList.add(localStringTokenizer.nextToken());
		}
		return localArrayList;
	}

	public static String replacePattern(String paramString1,
			String paramString2, String paramString3) {
		Pattern localPattern = Pattern.compile(paramString2);
		Matcher localMatcher = localPattern.matcher(paramString1);
		if (localMatcher.find())
			return localMatcher.replaceAll(paramString3);
		return paramString1;
	}

	public static String randomString(int paramInt1, int paramInt2) {
		int i = rand(paramInt1, paramInt2);
		byte[] arrayOfByte = new byte[i];
		for (int j = 0; j < i; j++)
			arrayOfByte[j] = ((byte) rand(97, 122));
		return new String(arrayOfByte);
	}

	public static int rand(int paramInt1, int paramInt2) {
		int i = paramInt2 - paramInt1 + 1;
		int j = rn.nextInt() % i;
		if (j < 0)
			j = -j;
		return paramInt1 + j;
	}

	public static String jsQuote(String paramString) {
		if (paramString != null) {
			String str = paramString.trim();
			str = replace(str, "\"", "\\\"");
			str = replace(str, "\\", "\\\\");
			str = replace(str, "'", "\\'");
			return str;
		}
		return "";
	}

	public static String jsEscape(String paramString) {
		if (paramString != null) {
			String str = paramString.trim();
			str = replace(str, "%", "%25");
			str = replace(str, "\r\n", "%0A");
			str = replace(str, "\r", "%0A");
			str = replace(str, "\n", "%0A");
			str = replace(str, "\"", "%22");
			str = replace(str, "\\", "%5C");
			str = replace(str, "!", "%21");
			str = replace(str, "@", "%40");
			str = replace(str, "#", "%23");
			str = replace(str, "$", "%24");
			str = replace(str, "^", "%5E");
			str = replace(str, "&", "%26");
			str = replace(str, "'", "%27");
			str = replace(str, "(", "%28");
			str = replace(str, ")", "%29");
			str = replace(str, "=", "%3D");
			str = replace(str, " ", "%20");
			str = replace(str, ",", "%2C");
			str = replace(str, "/", "%2F");
			str = replace(str, ":", "%3A");
			str = replace(str, ";", "%3B");
			str = replace(str, "<", "%3C");
			str = replace(str, ">", "%3E");
			str = replace(str, "?", "%3F");
			str = replace(str, "[", "%5B");
			str = replace(str, "]", "%5D");
			str = replace(str, "{", "%7B");
			str = replace(str, "}", "%7D");
			str = replace(str, "`", "%60");
			str = replace(str, "~", "%7E");
			return str;
		}
		return "";
	}

	public static String jsUnEscape(String paramString) {
		if (paramString != null) {
			String str = paramString.trim();
			str = replace(str, "%25", "%");
			str = replace(str, "%0A", "\n");
			str = replace(str, "%22", "\"");
			str = replace(str, "%5C", "\\");
			str = replace(str, "%21", "!");
			str = replace(str, "%40", "@");
			str = replace(str, "%23", "#");
			str = replace(str, "%24", "$");
			str = replace(str, "%5E", "^");
			str = replace(str, "%26", "&");
			str = replace(str, "%27", "'");
			str = replace(str, "%28", "(");
			str = replace(str, "%29", ")");
			str = replace(str, "%2B", "+");
			str = replace(str, "%3D", "=");
			str = replace(str, "%20", " ");
			str = replace(str, "%7C", "|");
			str = replace(str, "%2C", ",");
			str = replace(str, "%2F", "/");
			str = replace(str, "%3A", ":");
			str = replace(str, "%3B", ";");
			str = replace(str, "%3C", "<");
			str = replace(str, "%3E", ">");
			str = replace(str, "%3F", "?");
			str = replace(str, "%5B", "[");
			str = replace(str, "%5D", "]");
			str = replace(str, "%7B", "{");
			str = replace(str, "%7D", "}");
			str = replace(str, "%60", "`");
			str = replace(str, "%7E", "~");
			str = replace(str, "&apos;", "'");
			return str;
		}
		return "";
	}

	public static String jsStringEscape(String paramString) {
		if (paramString != null) {
			String str = paramString.trim();
			str = replace(str, "\\", "\\\\");
			str = replace(str, "'", "\\'");
			str = replace(str, "\"", "\\\"");
			str = replace(str, "&", "\\&");
			str = replace(str, "\n", "\\n");
			str = replace(str, "\r", "\\r");
			str = replace(str, "\t", "\\t");
			str = replace(str, "\b", "\\b");
			str = replace(str, "\f", "\\f");
			str = replace(str, "/", "\\/");
			return str;
		}
		return "";
	}

	public static String trimToSize(String paramString, int paramInt) {
		if ((paramString != null) && (paramString.length() > paramInt))
			return paramString.substring(0, paramInt - 1) + "...";
		return paramString;
	}

	public static String trimToSizeNoDots(String paramString, int paramInt) {
		if ((paramString != null) && (paramString.length() > paramInt))
			return paramString.substring(0, paramInt);
		return paramString;
	}

	public static boolean hasNumberCount(String paramString, int paramInt) {
		int i = 0;
		for (int j = 0; j < paramString.length(); j++)
			if ("0123456789".indexOf(paramString.charAt(j)) > -1)
				i++;
		return i == paramInt;
	}

	public static boolean isNumber(String paramString) {
		if (paramString == null)
			return false;
		if (paramString.length() == 0)
			return false;
		int i = 0;
		if (paramString.startsWith("-"))
			i++;
		if (paramString.contains("."))
			i++;
		for (int j = 0; j < paramString.length(); j++)
			if ("0123456789".indexOf(paramString.charAt(j)) > -1)
				i++;
		return i == paramString.length();
	}

	public static int countLines(String paramString) {
		if (paramString == null)
			return 0;
		int i = 0;
		try {
			BufferedReader localBufferedReader = new BufferedReader(
					new StringReader(paramString));
			while (localBufferedReader.readLine() != null)
				i++;
			localBufferedReader.close();
		} catch (Exception localException) {
			if (System.getProperty("DEBUG") != null)
				System.out.println("StringUtils-> Count exception: "
						+ localException.getMessage());
		}
		return i;
	}

	public static String convertFileToValidHTMLIncludeFile(String paramString1,
			String paramString2, String paramString3, String paramString4,
			String paramString5) throws Exception {
		paramString1 = paramString1.substring(
				paramString1.indexOf(paramString3),
				paramString1.indexOf(paramString4));
		StringBuffer localStringBuffer = new StringBuffer(paramString1);
		int i = 0;
		while (localStringBuffer.indexOf(paramString5, i) != -1) {
			i = localStringBuffer.indexOf(paramString5, i)
					+ paramString5.length();
			localStringBuffer.insert(i, paramString2);
		}
		return localStringBuffer.toString();
	}

	public static String convertFileToValidHTMLIncludeFile(File paramFile,
			String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5, String paramString6)
			throws Exception {
		String str = loadText(paramFile);
		if (str.indexOf(".shtml") > -1)
			return str;
		return convertFileNameToSHTML(str, paramString2, paramString3,
				paramString4, paramString5, paramString6);
	}

	public static String convertFileNameToSHTML(String paramString1,
			String paramString2, String paramString3, String paramString4,
			String paramString5, String paramString6) throws Exception {
		if ((paramString1.indexOf(paramString2) != -1)
				&& (paramString1.indexOf(paramString3) != -1)) {
			paramString1 = paramString1.substring(
					paramString1.indexOf(paramString2),
					paramString1.indexOf(paramString3));
			StringBuffer localStringBuffer = new StringBuffer(paramString1);
			int i = 0;
			while (localStringBuffer.indexOf(paramString4, i) != -1) {
				int j = localStringBuffer.indexOf(paramString4, i)
						+ paramString4.length();
				if (!localStringBuffer.substring(j, j + paramString5.length())
						.equalsIgnoreCase(paramString5)) {
					if (localStringBuffer.indexOf(paramString6,
							localStringBuffer.indexOf(paramString4, i)) != -1) {
						i = localStringBuffer.indexOf(paramString6,
								localStringBuffer.indexOf(paramString4, i));
						localStringBuffer.insert(i, 's');
					} else {
						i += localStringBuffer.substring(j,
								j + paramString5.length()).length();
					}
				} else
					i += localStringBuffer.substring(j,
							j + paramString5.length()).length();
			}
			return localStringBuffer.toString();
		}
		return paramString1;
	}

	public static boolean isPhoneNumber(String paramString) {
		if (paramString == null)
			return false;
		for (int i = 0; i < paramString.length(); i++)
			if (allowed.indexOf(paramString.charAt(i)) == -1)
				return false;
		return true;
	}

	public static boolean hasAllowedOnly(String paramString1,
			String paramString2) {
		for (int i = 0; i < paramString2.length(); i++)
			if (paramString1.indexOf(paramString2.charAt(i)) == -1)
				return false;
		return true;
	}

	public static String toAllowedOnly(String paramString1, String paramString2) {
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramString2.length(); i++)
			if (paramString1.indexOf(paramString2.charAt(i)) > -1)
				localStringBuffer.append(paramString2.charAt(i));
		return localStringBuffer.toString();
	}

	public static boolean includesAny(String paramString1, String paramString2) {
		for (int i = 0; i < paramString2.length(); i++)
			if (paramString1.indexOf(paramString2.charAt(i)) > -1)
				return true;
		return false;
	}

	public static String getText(String paramString1, String paramString2) {
		if (paramString1 == null)
			return paramString2;
		return paramString1;
	}

	public static String encodeUrl(String paramString) {
		if (paramString == null)
			return null;
		try {
			return URLEncoder.encode(paramString, "UTF-8");
		} catch (Exception localException) {
		}
		return paramString;
	}

	public static String capitalize(String paramString) {
		if (paramString == null)
			return null;
		if (paramString.length() == 1)
			return paramString.toUpperCase();
		return paramString.substring(0, 1).toUpperCase()
				+ paramString.substring(1);
	}

	public static String arrayToLineDelimitedString(
			ArrayList<String> paramArrayList) {
		return arrayToDelimitedString(paramArrayList, "\r\n");
	}

	public static String arrayToCommaSeparatedString(
			ArrayList<String> paramArrayList) {
		return arrayToDelimitedString(paramArrayList, ",");
	}

	public static String arrayToDelimitedString(
			ArrayList<String> paramArrayList, String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		int i = 0;
		Iterator localIterator = paramArrayList.iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			i++;
			if (i > 1)
				localStringBuffer.append(paramString);
			localStringBuffer.append(str);
		}
		return localStringBuffer.toString();
	}
}