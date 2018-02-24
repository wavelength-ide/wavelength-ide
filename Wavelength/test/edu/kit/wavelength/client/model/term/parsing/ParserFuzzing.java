package edu.kit.wavelength.client.model.term.parsing;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.ToStringVisitor;
import jwp.fuzz.BranchHit;
import jwp.fuzz.ByteArrayParamGenerator;
import jwp.fuzz.Fuzzer;
import jwp.fuzz.ParamProvider;

public class ParserFuzzing {
	public static void main(String[] args) throws Throwable {
		Set<Integer> seenPathHashes = Collections.newSetFromMap(new ConcurrentHashMap<>());

		Fuzzer fuzzer = new Fuzzer(Fuzzer.Config.builder()
				.method(ParserFuzzing.class.getDeclaredMethod("parse", String.class))
				.params(new ParamProvider.Suggested(ByteArrayParamGenerator.stringParamGenerator(
						StandardCharsets.ISO_8859_1,
						new ByteArrayParamGenerator(ByteArrayParamGenerator.Config.builder().build()).filter(bytes -> {
							for (byte b : bytes)
								if (b != 10 && (b < 32 || b > 126))
									return false;
							return true;
						}))
				))
				.onEachResult(res -> {
					int hash = BranchHit.Hasher.WITHOUT_HIT_COUNTS.hash(res.branchHits);

					if (seenPathHashes.add(hash))
						synchronized (Parser.class) {
							System.out.printf("Unique path for param '%s', %s\n", res.params[0],
									res.exception == null
											? ((LambdaTerm) res.result).acceptVisitor(new ToStringVisitor())
											: res.exception);
						}
				}).build());

		fuzzer.fuzzFor(10, TimeUnit.MINUTES);
	}

	public static LambdaTerm parse(String s) throws ParseException {
		return new Parser(Collections.emptyList()).parse(s);
	}
}
