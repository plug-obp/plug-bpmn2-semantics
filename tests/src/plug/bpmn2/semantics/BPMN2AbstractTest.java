package plug.bpmn2.semantics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.emf.ecore.EObject;
import org.junit.After;
import org.junit.Before;

import plug.bpmn2.module.BPMN2Loader;
import plug.bpmn2.semantics.transition.BPMN2AbstractTransition;

public abstract class BPMN2AbstractTest {

	protected BPMN2Loader loader;
	protected BPMN2TransitionFunction transitionFunction;
	protected List<BPMN2SystemConfiguration> knownList;
	protected int numberOfTransition;

	public abstract String getFilePath();

	public void load() {
		loader.loadModelFromFilePath(getFilePath());
	}

	public void buildTransitionFunction() {
		EObject modelRoot = loader.getModelObjectList().get(0);
		transitionFunction = new BPMN2TransitionFunction(modelRoot);
	}

	public void explore() {

		List<BPMN2SystemConfiguration> toSeeList = new LinkedList<>();
		toSeeList.addAll(transitionFunction.getInitialConfigurations());
		knownList.addAll(transitionFunction.getInitialConfigurations());

		while (!toSeeList.isEmpty()) {
			BPMN2SystemConfiguration source = toSeeList.remove(0);
			List<BPMN2AbstractTransition> transitionList = transitionFunction.getTransitionsFrom(source);

			for (BPMN2AbstractTransition transition : transitionList) {
				BPMN2SystemConfiguration target = transition.executeAction(source);
				numberOfTransition += 1;

				if (!knownList.contains(target)) {
					knownList.add(target);
					toSeeList.add(target);
				}

			}

		}

	}

	public List<BPMN2SystemConfiguration> getKnownList() {
		return knownList;
	}

	@Before
	public void before() {
		loader = new BPMN2Loader();
		knownList = new LinkedList<>();
		numberOfTransition = 0;

		load();
		buildTransitionFunction();
		// BPMN2ATSUtils.printFlow(transitionFunction); // soit en option
		explore();

	}

	@After
	public void after() {
		loader = null;
		knownList = null;
		numberOfTransition = 0;
	}

	public int getNumberOfTransition() {
		return numberOfTransition;
	}

	protected void assertSequenceFlow(EObject token, String sourceName, String targetName) {
		assertTrue(token instanceof SequenceFlow);
		SequenceFlow sequenceFlow = (SequenceFlow) token;
		assertEquals(sourceName, sequenceFlow.getSourceRef().getName());
		assertEquals(targetName, sequenceFlow.getTargetRef().getName());
	}

}