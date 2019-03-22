package plug.bpmn2.semantics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

import plug.bpmn2.semantics.transition.AbstractTransitionSimple;
import plug.bpmn2.semantics.transition.BPMN2AbstractTransition;
import plug.bpmn2.interpretation.tools.base.BPMN2PrinterShort;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2ToATS {

	public final Loader loader = new Loader();

	public BPMN2AbstractTransitionSystem transformECoreModel(EObject root) {
		BPMN2AbstractTransitionSystem transitionSystem = new BPMN2AbstractTransitionSystem();
		for (BPMN2AbstractTransition transition : loader.doSwitch(root)) {
			transitionSystem.getTransitionSet().add(transition);
			transitionSystem.getInitiatorSet().addAll(transition.getIncomingList());
		}

		return transitionSystem;
	}

	public class Loader extends Bpmn2Switch<List<BPMN2AbstractTransition>> {

		@Override
		public List<BPMN2AbstractTransition> caseDocumentRoot(DocumentRoot object) {
			return doSwitch(object.getDefinitions());
		}

		@Override
		public List<BPMN2AbstractTransition> caseDefinitions(Definitions object) {
			List<BPMN2AbstractTransition> result = new LinkedList<>();
			for (RootElement element : object.getRootElements()) {
				result.addAll(doSwitch(element));
			}
			return result;
		}

		@Override
		public List<BPMN2AbstractTransition> caseProcess(Process object) {
			List<BPMN2AbstractTransition> result = new LinkedList<>();
			for (FlowElement element : object.getFlowElements()) {
				result.addAll(doSwitch(element));
			}
			return result;
		}

		@Override
		public List<BPMN2AbstractTransition> caseFlowNode(FlowNode object) {
			System.out.println("Flow node detected: " + BPMN2PrinterShort.INSTANCE.getShortString(object));

			List<BPMN2AbstractTransition> transitionList = new LinkedList<>();
			for (SequenceFlow incomingFlow : object.getIncoming()) {
				BPMN2AbstractTransition transition = new AbstractTransitionSimple();
				transition.getIncomingList().add(incomingFlow);
				transition.getNodeList().add(object);
				transition.getOutgoingList().addAll(object.getOutgoing());
				transitionList.add(transition);
			}
			return transitionList;
		}

		@Override
		public List<BPMN2AbstractTransition> caseTask(Task object) {
			// TODO Auto-generated method stub
			System.out.println("Task detected: " + BPMN2PrinterShort.INSTANCE.getShortString(object));
			List<BPMN2AbstractTransition> transitionList = new LinkedList<>();
			BPMN2AbstractTransition transition = new AbstractTransitionSimple();
			transition.getIncomingList().addAll(object.getIncoming());
			transition.getNodeList().add(object);
			transition.getOutgoingList().addAll(object.getOutgoing());
			transitionList.add(transition);
			return transitionList;
		}

		@Override
		public List<BPMN2AbstractTransition> caseParallelGateway(ParallelGateway object) {
			System.out.println("Parallel Gateway detected: " + BPMN2PrinterShort.INSTANCE.getShortString(object));
			List<BPMN2AbstractTransition> transitionList = new LinkedList<>();
			BPMN2AbstractTransition transition = new AbstractTransitionSimple();
			transition.getIncomingList().addAll(object.getIncoming());
			transition.getNodeList().add(object);
			transition.getOutgoingList().addAll(object.getOutgoing());
			transitionList.add(transition);

			return transitionList;

		}

		@Override
		public List<BPMN2AbstractTransition> caseExclusiveGateway(ExclusiveGateway object) {
			// TODO Auto-generated method stub
			System.out.println("Exclusive Gateway detected: " + BPMN2PrinterShort.INSTANCE.getShortString(object));
			List<BPMN2AbstractTransition> transitionList = new LinkedList<>();
			for (SequenceFlow incomingFlow : object.getIncoming()) {
				for (SequenceFlow outgoingFlow : object.getOutgoing()) {
					BPMN2AbstractTransition transition = new AbstractTransitionSimple();
					transition.getIncomingList().add(incomingFlow);
					transition.getNodeList().add(object);
					transition.getOutgoingList().add(outgoingFlow);
					transitionList.add(transition);
				}
			}

			return transitionList;
		}



		@Override
		public List<BPMN2AbstractTransition> caseServiceTask(ServiceTask object) {
			object.getOperationRef();
			return super.caseServiceTask(object);
		}

		public List<List<SequenceFlow>> combi(List<SequenceFlow> list, int k) {
			List<List<SequenceFlow>> p = new ArrayList<List<SequenceFlow>>();
			int i = 0;
			int imax = (int) (Math.pow(2, list.size()) - 1);

			while (i <= imax) {
				List<SequenceFlow> s = new ArrayList<SequenceFlow>();
				int j = 0;
				int jmax = list.size() - 1;
				while (j <= jmax) {
					if (((i >> j) & 1) == 1) {
						s.add(list.get(j));
					}
					j = j + 1;
				}
				if (s.size() == k) {
					p.add(s);
				}

				i = i + 1;
			}
			return p;
		}

		@Override
		public List<BPMN2AbstractTransition> caseInclusiveGateway(InclusiveGateway object) {
			System.out.println("Inclusive Gateway detected: " + BPMN2PrinterShort.INSTANCE.getShortString(object));
			List<List<SequenceFlow>> listCombinaision = new ArrayList<List<SequenceFlow>>();
			for (int i = 1; i <= object.getOutgoing().size(); i++)
				listCombinaision.addAll(combi(object.getOutgoing(), i));

			List<BPMN2AbstractTransition> transitionList = new LinkedList<>();
			for (SequenceFlow incomingFlow : object.getIncoming()) {
				for (List<SequenceFlow> outgoingFlow : listCombinaision) {
					BPMN2AbstractTransition transition = new AbstractTransitionSimple();
					transition.getIncomingList().add(incomingFlow);
					transition.getNodeList().add(object);
					transition.getOutgoingList().addAll(outgoingFlow);
					transitionList.add(transition);
				}
			}
			System.out.println(transitionList.size());
			System.out.println(transitionList);
			return transitionList;
		}

		@Override
		public List<BPMN2AbstractTransition> caseEventBasedGateway(EventBasedGateway object) {
			System.out.println("EventBased Gateway detected: " + BPMN2PrinterShort.INSTANCE.getShortString(object));
			List<BPMN2AbstractTransition> transitionList = new LinkedList<>();
			for (SequenceFlow incomingFlow : object.getIncoming()) {
				for (SequenceFlow outgoingFlow : object.getOutgoing()) {
					BPMN2AbstractTransition transition = new AbstractTransitionSimple();
					transition.getIncomingList().add(incomingFlow);
					transition.getNodeList().add(object);
					transition.getOutgoingList().add(outgoingFlow);
					transitionList.add(transition);
				}
			}
			BPMN2AbstractTransition transition = new AbstractTransitionSimple();
			transition.getIncomingList().addAll(object.getIncoming());
			transition.getNodeList().add(object);
			transition.getOutgoingList().addAll(object.getIncoming());
			return transitionList;
		}

		@Override
		public List<BPMN2AbstractTransition> caseIntermediateCatchEvent(IntermediateCatchEvent object) {
			// TODO Auto-generated method stub
			System.out
					.println("Intermediante Cath Event detected: " + BPMN2PrinterShort.INSTANCE.getShortString(object));
			object.getDataOutputAssociation();

			return super.caseIntermediateCatchEvent(object);
		}

		@Override
		public List<BPMN2AbstractTransition> caseIntermediateThrowEvent(IntermediateThrowEvent object) {
			// TODO Auto-generated method stub
			System.out
					.println("Intermediate Throw Event detected: " + BPMN2PrinterShort.INSTANCE.getShortString(object));
			return null;

		}

		/*
		 * @Override public List<BPMN2AbstractTransition> caseThrowEvent(ThrowEvent
		 * object) { // TODO Auto-generated method stub return
		 * super.caseThrowEvent(object); }
		 */

		@Override
		public List<BPMN2AbstractTransition> defaultCase(EObject object) {
			return Collections.emptyList();
		}
	}

}
