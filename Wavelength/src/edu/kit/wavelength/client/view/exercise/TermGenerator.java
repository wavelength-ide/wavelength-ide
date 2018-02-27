package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;

import com.google.gwt.user.client.Random;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.export.BasicExportVisitor;

public class TermGenerator {

	private int minDepth;
	private int maxDepth;
	private int openNodes;
	
	int deepestTerm = 0;
	int deepestAbs = 0;

	public static void main(String[] args) {
		int minD = 3;
		int maxD = 10;
		TermGenerator tg = new TermGenerator();
		BasicExportVisitor bev = new BasicExportVisitor(new ArrayList<Library>(), "λ");
		for (int i = 0; i < 100; i++) {
			LambdaTerm someTerm = tg.buildTerm(minD, maxD);
			String result = someTerm.acceptVisitor(bev).toString();
			System.out.println(result);
			if (tg.deepestTerm < minD) {
				throw new IllegalArgumentException("too shallow " + tg.deepestTerm);
			}
			if (tg.deepestAbs > maxD) {
				throw new IllegalArgumentException("too deep " + tg.deepestAbs);
			}
		}
	}

	public LambdaTerm buildTerm(int minDepth, int maxDepth) {
		this.maxDepth = maxDepth;
		this.minDepth = minDepth;
		deepestTerm = 0;
		deepestAbs = 0;
		openNodes = 1;
		return makeTerm(0, 0);
	}

	private LambdaTerm makeTerm(int termDepth, int abstractionDepth) {
		if (termDepth > deepestTerm) {
			deepestTerm = termDepth;
		}
		if (abstractionDepth > deepestAbs) {
			deepestAbs = abstractionDepth;
		}
		
		int random = Random.nextInt(4);
		if (termDepth == maxDepth) {
			// forces variable
			random = (random % 2) + 2;
		} else if (termDepth < minDepth) {
			if (openNodes == 1) {
				// force further abstraction or application
				random = random % 2;
			}
		}	
		switch (random) {
		case 0:
			String absVariable = getRandomVarName();
			return new Abstraction(absVariable, makeTerm(termDepth + 1, abstractionDepth + 1));
		case 1:
			openNodes++;
			LambdaTerm left = makeTerm(termDepth + 1, abstractionDepth);
			LambdaTerm right = makeTerm(termDepth + 1, abstractionDepth);
			return new Application(left, right);
		case 2:
			if (abstractionDepth > 0) {
				openNodes--;
				int randomIndex = Random.nextInt(abstractionDepth) + 1;
				return new BoundVariable(randomIndex);
			}
		default:
			openNodes--;
			return new FreeVariable(getRandomVarName());
		}
	}

	private String getRandomVarName() {
		char result = (char) (Random.nextInt('z' - 'a' + 1) + 'a');
		return "" + result;
	}
}