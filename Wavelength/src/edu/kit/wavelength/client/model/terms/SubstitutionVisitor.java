package edu.kit.wavelength.client.model.terms;

public class SubstitutionVisitor extends TermTransformer {
	
	private final int depth;
	private final LambdaTerm substituent;
	
	public SubstitutionVisitor(int depth, LambdaTerm substituent) {
		this.depth = depth;
		this.substituent = substituent;
	}

	@Override
	public LambdaTerm visitAbstraction(Abstraction abs) {
		return null;
	}

	@Override
	public LambdaTerm visitApplication(Application app) {
		return null;
	}

	@Override
	public LambdaTerm visitBoundVariable(BoundVariable var) {
		return null;
	}

	@Override
	public LambdaTerm visitFreeVariable(FreeVariable var) {
		return null;
	}

}
