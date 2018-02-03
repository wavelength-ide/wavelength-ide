package edu.kit.wavelength.client.model.term.parsing;

public enum TokenType {
	LBRACKET {
		@Override
		public String toString() {
			return "(";
		}
	},
	RBRACKET {
		@Override
		public String toString() {
			return ")";
		}
	},
	NAME {
		@Override
		public String toString() {
			return "variable";
		}
	},
	LAMBDA {
		@Override
		public String toString() {
			return "λ";
		}
	},
	DOT {
		@Override
		public String toString() {
			return ".";
		}
	},
	EQUALS {
		@Override
		public String toString() {
			return "=";
		}
	},
	NEWLINE {
		@Override
		public String toString() {
			return null;
			//return System.getProperty("line.separator");
		}
	},
	SPACE {
		@Override
		public String toString() {
			return "whitespace";
		}
	}
}
