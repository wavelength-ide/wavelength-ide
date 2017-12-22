package edu.kit.wavelet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.sun.javadoc.*;

public class Wavelet {
	static void emitType(PrintWriter out, Type t) {
		if (t.qualifiedTypeName().startsWith("edu.kit.wavelength"))
		{
			out.write("\\hyperref[type:");
			out.write(t.qualifiedTypeName());
			out.write("]{");
			out.write(t.typeName());
			out.write("}");
		}
		else
		{
			out.write(t.typeName());
		}
	}
	
	public static boolean start(RootDoc root)
	{
		PrintWriter out;
		try
		{
			out = new PrintWriter(
				new BufferedWriter(
						new FileWriter("build/doclet/out.tex")));
		}
		catch (Throwable t)
		{
			// sdjfsdjfjdjhrfbsejfhsaiofuehfi
			return false;
		}
        PackageDoc[] packages = root.specifiedPackages();
        for (int i = 0; i < packages.length; ++i) {
        	out.write("\\subsection{Package \\lstinline{");
        	out.write(packages[i].name());
        	out.write("}}");
        	out.write("\n");
        	
        	out.write("\\input{overview/");
        	out.write(packages[i].name());
        	out.write(".tex}\n\n");
        	
        	for (int j = 0; j < packages[i].allClasses().length; ++j) {
        		ClassDoc cl = packages[i].allClasses()[j];
        		
        		out.write("\\subsubsection{");
        		if (cl.isClass())
        			out.write("Class");
        		if (cl.isInterface())
        			out.write("Interface");
        		out.write(" \\lstinline{");
        		out.write(cl.typeName());
        		out.write("}}\n");
        		
        		out.write("\\label{type:");
        		out.write(cl.qualifiedTypeName());
        		out.write("}\n");
        		
        		ClassDoc sup = cl.superclass();
        		if (sup != null && !sup.typeName().equals("Object")
        				&& !sup.typeName().equals("Enum"))
        		{
        			out.write("Extends: \\texttt{");
        			emitType(out, sup);
        			out.write("}\n\n");
        		}
        		
        		ClassDoc[] in = cl.interfaces();
        		if (in.length > 0)
        		{
        			out.write("Implements: ");
        			for (int k = 0; k < in.length; ++k) {
        				if (k > 0)
        					out.write(", ");
        				out.write("\\texttt{");
        				emitType(out, in[k]);
        				out.write("}");
        			}
        			out.write("\n\n");
        		}
        		
        		out.write(cl.commentText());
        		out.write("\n\n");
        		
        		ConstructorDoc[] c = cl.constructors();
        		if (c.length > 0) {
        			out.write("Constructors:\n");
        			out.write("\\begin{itemize}\n");
        			for (int k = 0; k < c.length; ++k) {
        				out.write("\\item \\texttt{");
        				out.write(c[k].name());
        				out.write("(");
        				Parameter[] p = c[k].parameters();
        				for (int l = 0; l < p.length; ++l) {
        					if (l > 0)
        						out.write(", ");
        					out.write(p[l].typeName());
        					out.write(" ");
        					out.write(p[l].name());
        				}
        				out.write(")");
        				out.write("}\n\n");
        				out.write(c[k].commentText());
        				out.write("\n\n");
        				Tag[] pars = c[k].tags("param");
        				for (int l = 0; l < pars.length; ++l) {
        					ParamTag t = (ParamTag)pars[l];
        					out.write("\\texttt{");
        					out.write(t.parameterName());
        					out.write("}: ");
        					out.write(t.parameterComment());
        					out.write("\n\n");
        				}
        			}
        			out.write("\\end{itemize}\n\n");
        		}
        		
        		MethodDoc[] m_ = cl.methods();
        		ArrayList<MethodDoc> m = new ArrayList<MethodDoc>();
        		
        		for (int k = 0; k < m_.length; ++k) {
        			AnnotationDesc[] ann = m_[k].annotations();
        			boolean over = false;
        			for (int l = 0; l < ann.length; ++l) {
        				if (ann[l].annotationType().typeName().equals("Override"))
        					over = true;
        			}
        			if (!over)
        				m.add(m_[k]);
        		}
        	
        		if (m.size() > 0) {
        			out.write("Methods:\n");
        			out.write("\\begin{itemize}\n");
        			for (int k = 0; k < m.size(); ++k) {
        				
        				out.write("\\item \\texttt{");
        				out.write(m.get(k).returnType().typeName());
        				out.write(" ");
        				out.write(m.get(k).name());
        				out.write("(");
        				Parameter[] p = m.get(k).parameters();
        				for (int l = 0; l < p.length; ++l) {
        					if (l > 0)
        						out.write(", ");
        					out.write(p[l].typeName());
        					out.write(" ");
        					out.write(p[l].name());
        				}
        				out.write(")");
        				out.write("}\n\n");
        				out.write(m.get(k).commentText());
        				out.write("\n\n");
        				ParamTag[] pars = m.get(k).paramTags();
        				for (int l = 0; l < pars.length; ++l) {
        					ParamTag t = pars[l];
        					out.write("\\texttt{");
        					out.write(t.parameterName());
        					out.write("}: ");
        					out.write(t.parameterComment());
        					out.write("\n\n");
        				}
        			}
        			out.write("\\end{itemize}\n\n");
        		}
        	}
        }
        out.close();
        return true;
	}
}
