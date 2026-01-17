package obp2.bpmn2.pocs;

import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;

public class FCReworkedModel {



    static public void main(String[] args) {
        System.out.println("Loading ...");
        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_REWORKED_FILE_PATH);
        FBAllExplorationsPerProcess.exploreAllProcessesAsRootProcess(documentRoot, 100000);
    }

}

/*
Loading ...
Exploring with "process 7" as the root process ...
Regular Exploration
[BFS]10479 states, PT0.614S, maxWidth: 39, maxFrontier: 89523, known: 100002, maxTokens: 39, nbDeadlocks: 0
[DFS]99312 states, PT1.049S, maxWidth: 39, maxFrontier: 758, known: 100001, maxTokens: 39, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0.003S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 39, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 39, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 39, nbDeadlocks: 0
[DFS]5 states, PT0.001S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 39, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 39
[DFS]5 states, PT0.001S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 39

Exploring with "process 25" as the root process ...
Regular Exploration
[BFS]49912 states, PT0.377S, maxWidth: 13, maxFrontier: 50088, known: 100000, maxTokens: 16, nbDeadlocks: 0
[DFS]99949 states, PT0.454S, maxWidth: 10, maxFrontier: 124, known: 100002, maxTokens: 18, nbDeadlocks: 0
Flow completion Exploration
[BFS]4201 states, PT0.046S, maxWidth: 12, maxFrontier: 989, known: 4201, maxTokens: 18, nbDeadlocks: 0
[DFS]4201 states, PT0.052S, maxWidth: 12, maxFrontier: 68, known: 4201, maxTokens: 18, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]4201 states, PT0.034S, maxWidth: 12, maxFrontier: 989, known: 4201, maxTokens: 18, nbDeadlocks: 0
[DFS]4201 states, PT0.034S, maxWidth: 12, maxFrontier: 68, known: 4201, maxTokens: 18, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]4195 states, PT0.039S, maxWidth: 12, maxFrontier: 989, known: 4195, maxTokens: 18
[DFS]4195 states, PT0.043S, maxWidth: 12, maxFrontier: 68, known: 4195, maxTokens: 18

Exploring with "process 12" as the root process ...
Regular Exploration
[BFS]11 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 11, maxTokens: 1, nbDeadlocks: 0
[DFS]11 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 11, maxTokens: 1, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0.001S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1

Exploring with "process 16" as the root process ...
Regular Exploration
[BFS]30046 states, PT0.351S, maxWidth: 24, maxFrontier: 69955, known: 100001, maxTokens: 24, nbDeadlocks: 0
[DFS]99199 states, PT0.779S, maxWidth: 11, maxFrontier: 826, known: 100000, maxTokens: 28, nbDeadlocks: 0
Flow completion Exploration
[BFS]13925 states, PT2.052S, maxWidth: 28, maxFrontier: 86075, known: 100000, maxTokens: 28, nbDeadlocks: 0
[DFS]99398 states, PT3.612S, maxWidth: 28, maxFrontier: 678, known: 100005, maxTokens: 28, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]13925 states, PT2.16S, maxWidth: 28, maxFrontier: 86075, known: 100000, maxTokens: 28, nbDeadlocks: 0
[DFS]99398 states, PT3.468S, maxWidth: 28, maxFrontier: 678, known: 100005, maxTokens: 28, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]14619 states, PT2.389S, maxWidth: 28, maxFrontier: 85394, known: 100013, maxTokens: 28
[DFS]99388 states, PT3.404S, maxWidth: 28, maxFrontier: 677, known: 100000, maxTokens: 28

Exploring with "process 28" as the root process ...
Regular Exploration
[BFS]50578 states, PT0.512S, maxWidth: 19, maxFrontier: 49423, known: 100001, maxTokens: 28, nbDeadlocks: 0
[DFS]99082 states, PT1.061S, maxWidth: 8, maxFrontier: 1157, known: 100000, maxTokens: 57, nbDeadlocks: 0
Flow completion Exploration
[BFS]15511 states, PT3.115S, maxWidth: 34, maxFrontier: 84499, known: 100010, maxTokens: 40, nbDeadlocks: 0
[DFS]99508 states, PT6.067S, maxWidth: 17, maxFrontier: 527, known: 100000, maxTokens: 59, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]15511 states, PT3.185S, maxWidth: 34, maxFrontier: 84499, known: 100010, maxTokens: 40, nbDeadlocks: 0
[DFS]99508 states, PT6.108S, maxWidth: 17, maxFrontier: 527, known: 100000, maxTokens: 59, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]16510 states, PT3.219S, maxWidth: 34, maxFrontier: 83504, known: 100014, maxTokens: 40
[DFS]99515 states, PT5.818S, maxWidth: 17, maxFrontier: 565, known: 100001, maxTokens: 59

Exploring with "process 22" as the root process ...
Regular Exploration
[BFS]15376 states, PT0.882S, maxWidth: 37, maxFrontier: 84624, known: 100000, maxTokens: 6+43, nbDeadlocks: 0
[DFS]93845 states, PT3.545S, maxWidth: 21, maxFrontier: 6382, known: 100002, maxTokens: 6+98, nbDeadlocks: 1
Flow completion Exploration
[BFS]2634 states, PT11.659S, maxWidth: 94, maxFrontier: 97379, known: 100013, maxTokens: 6+125, nbDeadlocks: 0
[DFS]80841 states, PT9.596S, maxWidth: 99, maxFrontier: 19246, known: 100002, maxTokens: 6+154, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]65 states, PT0S, maxWidth: 6, maxFrontier: 23, known: 65, maxTokens: 6, nbDeadlocks: 0
[DFS]65 states, PT0S, maxWidth: 6, maxFrontier: 16, known: 65, maxTokens: 6, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]65 states, PT0S, maxWidth: 6, maxFrontier: 23, known: 65, maxTokens: 6
[DFS]65 states, PT0S, maxWidth: 6, maxFrontier: 16, known: 65, maxTokens: 6

Exploring with "process 10" as the root process ...
Regular Exploration
[BFS]41310 states, PT0.411S, maxWidth: 19, maxFrontier: 58690, known: 100000, maxTokens: 19, nbDeadlocks: 0
[DFS]99868 states, PT0.58S, maxWidth: 19, maxFrontier: 180, known: 100000, maxTokens: 19, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 19, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 19, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 19, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 19, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 19
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 19

Exploring with "process 18" as the root process ...
Regular Exploration
[BFS]60121 states, PT0.261S, maxWidth: 10, maxFrontier: 39879, known: 100000, maxTokens: 11, nbDeadlocks: 0
[DFS]99936 states, PT0.312S, maxWidth: 7, maxFrontier: 123, known: 100000, maxTokens: 11, nbDeadlocks: 0
Flow completion Exploration
[BFS]24001 states, PT0.166S, maxWidth: 10, maxFrontier: 3448, known: 24001, maxTokens: 11, nbDeadlocks: 0
[DFS]24001 states, PT0.179S, maxWidth: 10, maxFrontier: 82, known: 24001, maxTokens: 11, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]24001 states, PT0.176S, maxWidth: 10, maxFrontier: 3448, known: 24001, maxTokens: 11, nbDeadlocks: 0
[DFS]24001 states, PT0.175S, maxWidth: 10, maxFrontier: 82, known: 24001, maxTokens: 11, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]23702 states, PT0.146S, maxWidth: 10, maxFrontier: 3414, known: 23702, maxTokens: 11
[DFS]23702 states, PT0.141S, maxWidth: 10, maxFrontier: 82, known: 23702, maxTokens: 11

Exploring with "process 9" as the root process ...
Regular Exploration
[BFS]3671 states, PT0.538S, maxWidth: 77, maxFrontier: 96332, known: 100003, maxTokens: 78, nbDeadlocks: 0
[DFS]97101 states, PT1.663S, maxWidth: 77, maxFrontier: 2987, known: 100002, maxTokens: 78, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 78, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 78, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 78, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 78, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0.001S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 78
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 78

Exploring with "process 26" as the root process ...
Regular Exploration
[BFS]38232 states, PT0.266S, maxWidth: 15, maxFrontier: 61772, known: 100002, maxTokens: 16, nbDeadlocks: 0
[DFS]99781 states, PT0.346S, maxWidth: 7, maxFrontier: 272, known: 100000, maxTokens: 17, nbDeadlocks: 0
Flow completion Exploration
[BFS]39996 states, PT0.86S, maxWidth: 15, maxFrontier: 60005, known: 100001, maxTokens: 17, nbDeadlocks: 0
[DFS]99805 states, PT0.911S, maxWidth: 15, maxFrontier: 250, known: 100000, maxTokens: 16, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]39996 states, PT0.838S, maxWidth: 15, maxFrontier: 60005, known: 100001, maxTokens: 17, nbDeadlocks: 0
[DFS]99805 states, PT0.924S, maxWidth: 15, maxFrontier: 250, known: 100000, maxTokens: 16, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]41666 states, PT0.883S, maxWidth: 15, maxFrontier: 58337, known: 100003, maxTokens: 17
[DFS]99812 states, PT0.873S, maxWidth: 15, maxFrontier: 250, known: 100000, maxTokens: 16

Exploring with "process 21" as the root process ...
Regular Exploration
[BFS]60690 states, PT0.664S, maxWidth: 15, maxFrontier: 39311, known: 100001, maxTokens: 19, nbDeadlocks: 0
[DFS]97728 states, PT1.051S, maxWidth: 14, maxFrontier: 2373, known: 100002, maxTokens: 35, nbDeadlocks: 0
Flow completion Exploration
[BFS]36604 states, PT3.34S, maxWidth: 19, maxFrontier: 63396, known: 100000, maxTokens: 32, nbDeadlocks: 0
[DFS]99678 states, PT5.763S, maxWidth: 12, maxFrontier: 365, known: 100000, maxTokens: 39, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]36604 states, PT3.343S, maxWidth: 19, maxFrontier: 63396, known: 100000, maxTokens: 32, nbDeadlocks: 0
[DFS]99678 states, PT5.294S, maxWidth: 12, maxFrontier: 365, known: 100000, maxTokens: 39, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]38219 states, PT4.319S, maxWidth: 19, maxFrontier: 61782, known: 100000, maxTokens: 32
[DFS]99698 states, PT5.067S, maxWidth: 12, maxFrontier: 362, known: 100000, maxTokens: 39

Exploring with "process 2" as the root process ...
Regular Exploration
[BFS]7275 states, PT0.425S, maxWidth: 46, maxFrontier: 92751, known: 100026, maxTokens: 47, nbDeadlocks: 0
[DFS]98981 states, PT1.127S, maxWidth: 46, maxFrontier: 1076, known: 100000, maxTokens: 47, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 47, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 47, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 47, nbDeadlocks: 0
[DFS]5 states, PT0.001S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 47, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 47
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 47

Exploring with "process 20" as the root process ...
Regular Exploration
[BFS]16936 states, PT0.401S, maxWidth: 37, maxFrontier: 83073, known: 100009, maxTokens: 49, nbDeadlocks: 0
[DFS]96900 states, PT1.951S, maxWidth: 21, maxFrontier: 3177, known: 100000, maxTokens: 110, nbDeadlocks: 0
Flow completion Exploration
[BFS]4061 states, PT3.252S, maxWidth: 70, maxFrontier: 95958, known: 100019, maxTokens: 88, nbDeadlocks: 0
[DFS]97538 states, PT8.868S, maxWidth: 70, maxFrontier: 2501, known: 100002, maxTokens: 104, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]4061 states, PT3.22S, maxWidth: 70, maxFrontier: 95958, known: 100019, maxTokens: 88, nbDeadlocks: 0
[DFS]97538 states, PT8.831S, maxWidth: 70, maxFrontier: 2501, known: 100002, maxTokens: 104, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]4372 states, PT3.343S, maxWidth: 70, maxFrontier: 95637, known: 100009, maxTokens: 88
[DFS]97544 states, PT8.72S, maxWidth: 70, maxFrontier: 2497, known: 100001, maxTokens: 104

Exploring with "process 0" as the root process ...
callActivity 22 has no callable process (handled as a task)
callActivity 23 has no callable process (handled as a task)
parallelGateway 65 has no incoming sequence flows (ignored)
Regular Exploration
[BFS]6839 states, PT1.108S, maxWidth: 62, maxFrontier: 93184, known: 100023, maxTokens: 12+98, nbDeadlocks: 0
[DFS]94018 states, PT51.76S, maxWidth: 49, maxFrontier: 6044, known: 100000, maxTokens: 12+631, nbDeadlocks: 1
Flow completion Exploration
[BFS]19724 states, PT4M14.229S, maxWidth: 39, maxFrontier: 80278, known: 100002, maxTokens: 12+629, nbDeadlocks: 0
[DFS]76127 states, PT1M19.204S, maxWidth: 59, maxFrontier: 24004, known: 100000, maxTokens: 12+653, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]4097 states, PT0.015S, maxWidth: 12, maxFrontier: 988, known: 4097, maxTokens: 12, nbDeadlocks: 0
[DFS]4097 states, PT0.016S, maxWidth: 12, maxFrontier: 67, known: 4097, maxTokens: 12, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]4083 states, PT0.016S, maxWidth: 12, maxFrontier: 984, known: 4083, maxTokens: 12
[DFS]4083 states, PT0.015S, maxWidth: 12, maxFrontier: 67, known: 4083, maxTokens: 12

Exploring with "process 4" as the root process ...
Regular Exploration
[BFS]3592 states, PT0.71S, maxWidth: 82, maxFrontier: 96427, known: 100019, maxTokens: 82, nbDeadlocks: 0
[DFS]96659 states, PT2.119S, maxWidth: 82, maxFrontier: 3422, known: 100000, maxTokens: 82, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 82, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 82, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 82, nbDeadlocks: 0
[DFS]5 states, PT0.001S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 82, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 82
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 82

Exploring with "process 14" as the root process ...
Regular Exploration
[BFS]7182 states, PT0.01S, maxWidth: 6, maxFrontier: 736, known: 7182, maxTokens: 7, nbDeadlocks: 0
[DFS]7182 states, PT0.01S, maxWidth: 6, maxFrontier: 78, known: 7182, maxTokens: 7, nbDeadlocks: 0
Flow completion Exploration
[BFS]49 states, PT0S, maxWidth: 5, maxFrontier: 17, known: 49, maxTokens: 7, nbDeadlocks: 0
[DFS]49 states, PT0S, maxWidth: 5, maxFrontier: 12, known: 49, maxTokens: 7, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]49 states, PT0.001S, maxWidth: 5, maxFrontier: 17, known: 49, maxTokens: 7, nbDeadlocks: 0
[DFS]49 states, PT0S, maxWidth: 5, maxFrontier: 12, known: 49, maxTokens: 7, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]49 states, PT0S, maxWidth: 5, maxFrontier: 17, known: 49, maxTokens: 7
[DFS]49 states, PT0S, maxWidth: 5, maxFrontier: 12, known: 49, maxTokens: 7

Exploring with "process 24" as the root process ...
Regular Exploration
[BFS]14113 states, PT0.443S, maxWidth: 33, maxFrontier: 85890, known: 100003, maxTokens: 36, nbDeadlocks: 0
[DFS]98775 states, PT0.566S, maxWidth: 27, maxFrontier: 1280, known: 100001, maxTokens: 36, nbDeadlocks: 0
Flow completion Exploration
[BFS]13673 states, PT1.683S, maxWidth: 31, maxFrontier: 86331, known: 100004, maxTokens: 36, nbDeadlocks: 0
[DFS]99286 states, PT2.91S, maxWidth: 30, maxFrontier: 852, known: 100000, maxTokens: 37, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]13673 states, PT1.635S, maxWidth: 31, maxFrontier: 86331, known: 100004, maxTokens: 36, nbDeadlocks: 0
[DFS]99286 states, PT2.699S, maxWidth: 30, maxFrontier: 852, known: 100000, maxTokens: 37, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]14267 states, PT1.781S, maxWidth: 31, maxFrontier: 85738, known: 100005, maxTokens: 36
[DFS]99274 states, PT2.554S, maxWidth: 30, maxFrontier: 852, known: 100002, maxTokens: 37

Exploring with "process 19" as the root process ...
Regular Exploration
parallelGateway 65 has no incoming sequence flows (ignored)
[BFS]10839 states, PT0.564S, maxWidth: 49, maxFrontier: 89162, known: 100001, maxTokens: 51, nbDeadlocks: 0
[DFS]87709 states, PT2.278S, maxWidth: 42, maxFrontier: 12412, known: 100000, maxTokens: 95, nbDeadlocks: 0
Flow completion Exploration
[BFS]7144 states, PT6.917S, maxWidth: 58, maxFrontier: 92863, known: 100007, maxTokens: 87, nbDeadlocks: 0
[DFS]96467 states, PT12.465S, maxWidth: 45, maxFrontier: 3594, known: 100001, maxTokens: 89, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
parallelGateway 65 has no incoming sequence flows (ignored)
[BFS]7144 states, PT6.79S, maxWidth: 58, maxFrontier: 92863, known: 100007, maxTokens: 87, nbDeadlocks: 0
[DFS]96467 states, PT12.549S, maxWidth: 45, maxFrontier: 3594, known: 100001, maxTokens: 89, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]7622 states, PT7.229S, maxWidth: 58, maxFrontier: 92405, known: 100027, maxTokens: 87
[DFS]96488 states, PT12.002S, maxWidth: 45, maxFrontier: 3583, known: 100000, maxTokens: 89

Exploring with "process 5" as the root process ...
Regular Exploration
[BFS]7817 states, PT0.437S, maxWidth: 44, maxFrontier: 92185, known: 100002, maxTokens: 44, nbDeadlocks: 0
[DFS]99108 states, PT1.111S, maxWidth: 44, maxFrontier: 955, known: 100000, maxTokens: 44, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 44, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 44, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 44, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 44, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 44
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 44

Exploring with "process 29" as the root process ...
Regular Exploration
[BFS]55199 states, PT0.346S, maxWidth: 13, maxFrontier: 44801, known: 100000, maxTokens: 14, nbDeadlocks: 0
[DFS]99702 states, PT0.421S, maxWidth: 8, maxFrontier: 336, known: 100000, maxTokens: 19, nbDeadlocks: 0
Flow completion Exploration
[BFS]80145 states, PT1.846S, maxWidth: 12, maxFrontier: 19885, known: 100000, maxTokens: 18, nbDeadlocks: 0
[DFS]99972 states, PT2.11S, maxWidth: 11, maxFrontier: 95, known: 100000, maxTokens: 19, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]80145 states, PT1.878S, maxWidth: 12, maxFrontier: 19885, known: 100000, maxTokens: 18, nbDeadlocks: 0
[DFS]99972 states, PT2.001S, maxWidth: 11, maxFrontier: 95, known: 100000, maxTokens: 19, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]82064 states, PT1.882S, maxWidth: 12, maxFrontier: 18246, known: 100000, maxTokens: 18
[DFS]99917 states, PT1.875S, maxWidth: 11, maxFrontier: 96, known: 100000, maxTokens: 19

Exploring with "process 6" as the root process ...
Regular Exploration
[BFS]12 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 12, maxTokens: 1, nbDeadlocks: 0
[DFS]12 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 12, maxTokens: 1, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1

Exploring with "process 17" as the root process ...
Regular Exploration
[BFS]71439 states, PT0.38S, maxWidth: 12, maxFrontier: 28561, known: 100000, maxTokens: 13, nbDeadlocks: 0
[DFS]99578 states, PT0.341S, maxWidth: 11, maxFrontier: 592, known: 100000, maxTokens: 14, nbDeadlocks: 0
Flow completion Exploration
[BFS]9325 states, PT0.08S, maxWidth: 8, maxFrontier: 605, known: 9325, maxTokens: 14, nbDeadlocks: 0
[DFS]9325 states, PT0.075S, maxWidth: 8, maxFrontier: 114, known: 9325, maxTokens: 14, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]9325 states, PT0.078S, maxWidth: 8, maxFrontier: 605, known: 9325, maxTokens: 14, nbDeadlocks: 0
[DFS]9325 states, PT0.082S, maxWidth: 8, maxFrontier: 114, known: 9325, maxTokens: 14, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]9272 states, PT0.078S, maxWidth: 8, maxFrontier: 604, known: 9272, maxTokens: 14
[DFS]9272 states, PT0.075S, maxWidth: 8, maxFrontier: 114, known: 9272, maxTokens: 14

Exploring with "process 31" as the root process ...
Regular Exploration
[BFS]606 states, PT0S, maxWidth: 3, maxFrontier: 54, known: 606, maxTokens: 3, nbDeadlocks: 0
[DFS]606 states, PT0.001S, maxWidth: 3, maxFrontier: 36, known: 606, maxTokens: 3, nbDeadlocks: 0
Flow completion Exploration
[BFS]53 states, PT0S, maxWidth: 3, maxFrontier: 11, known: 53, maxTokens: 3, nbDeadlocks: 0
[DFS]53 states, PT0S, maxWidth: 3, maxFrontier: 10, known: 53, maxTokens: 3, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]53 states, PT0S, maxWidth: 3, maxFrontier: 11, known: 53, maxTokens: 3, nbDeadlocks: 0
[DFS]53 states, PT0.001S, maxWidth: 3, maxFrontier: 10, known: 53, maxTokens: 3, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]53 states, PT0S, maxWidth: 3, maxFrontier: 11, known: 53, maxTokens: 3
[DFS]53 states, PT0S, maxWidth: 3, maxFrontier: 10, known: 53, maxTokens: 3

Exploring with "process 15" as the root process ...
Regular Exploration
[BFS]6 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 6, maxTokens: 1, nbDeadlocks: 0
[DFS]6 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 6, maxTokens: 1, nbDeadlocks: 0
Flow completion Exploration
[BFS]3 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 3, maxTokens: 1, nbDeadlocks: 0
[DFS]3 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 3, maxTokens: 1, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]3 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 3, maxTokens: 1, nbDeadlocks: 0
[DFS]3 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 3, maxTokens: 1, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]3 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 3, maxTokens: 1
[DFS]3 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 3, maxTokens: 1

Exploring with "process 30" as the root process ...
Regular Exploration
[BFS]47602 states, PT0.572S, maxWidth: 18, maxFrontier: 52399, known: 100001, maxTokens: 21, nbDeadlocks: 0
[DFS]99628 states, PT0.501S, maxWidth: 12, maxFrontier: 449, known: 100000, maxTokens: 18, nbDeadlocks: 0
Flow completion Exploration
[BFS]62824 states, PT2.138S, maxWidth: 13, maxFrontier: 37178, known: 100002, maxTokens: 24, nbDeadlocks: 0
[DFS]99930 states, PT1.863S, maxWidth: 10, maxFrontier: 115, known: 100000, maxTokens: 20, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]62824 states, PT2.055S, maxWidth: 13, maxFrontier: 37178, known: 100002, maxTokens: 24, nbDeadlocks: 0
[DFS]99930 states, PT1.751S, maxWidth: 10, maxFrontier: 115, known: 100000, maxTokens: 20, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]64973 states, PT2.108S, maxWidth: 13, maxFrontier: 35040, known: 100001, maxTokens: 24
[DFS]99923 states, PT1.691S, maxWidth: 11, maxFrontier: 115, known: 100000, maxTokens: 20

Exploring with "null" as the root process ...
Regular Exploration
[BFS]8808 states, PT0.416S, maxWidth: 38, maxFrontier: 91200, known: 100008, maxTokens: 38, nbDeadlocks: 0
[DFS]97985 states, PT0.334S, maxWidth: 38, maxFrontier: 2110, known: 100000, maxTokens: 38, nbDeadlocks: 0
Flow completion Exploration
[BFS]2 states, PT0.002S, maxWidth: 38, maxFrontier: 1, known: 2, maxTokens: 38, nbDeadlocks: 0
[DFS]2 states, PT0.003S, maxWidth: 38, maxFrontier: 1, known: 2, maxTokens: 38, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]2 states, PT0.002S, maxWidth: 38, maxFrontier: 1, known: 2, maxTokens: 38, nbDeadlocks: 0
[DFS]2 states, PT0.002S, maxWidth: 38, maxFrontier: 1, known: 2, maxTokens: 38, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]2 states, PT0.002S, maxWidth: 38, maxFrontier: 1, known: 2, maxTokens: 38
[DFS]2 states, PT0.003S, maxWidth: 38, maxFrontier: 1, known: 2, maxTokens: 38

Exploring with "process 23" as the root process ...
Regular Exploration
[BFS]36322 states, PT0.314S, maxWidth: 15, maxFrontier: 63679, known: 100001, maxTokens: 16, nbDeadlocks: 0
[DFS]99427 states, PT0.495S, maxWidth: 10, maxFrontier: 765, known: 100003, maxTokens: 26, nbDeadlocks: 0
Flow completion Exploration
[BFS]44515 states, PT1.52S, maxWidth: 16, maxFrontier: 55487, known: 100002, maxTokens: 22, nbDeadlocks: 0
[DFS]99750 states, PT1.911S, maxWidth: 13, maxFrontier: 278, known: 100002, maxTokens: 34, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]44515 states, PT1.514S, maxWidth: 16, maxFrontier: 55487, known: 100002, maxTokens: 22, nbDeadlocks: 0
[DFS]99750 states, PT1.884S, maxWidth: 13, maxFrontier: 278, known: 100002, maxTokens: 34, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]46337 states, PT1.825S, maxWidth: 16, maxFrontier: 53663, known: 100000, maxTokens: 22
[DFS]99799 states, PT1.88S, maxWidth: 13, maxFrontier: 278, known: 100001, maxTokens: 34

Exploring with "process 27" as the root process ...
Regular Exploration
[BFS]24384 states, PT0.357S, maxWidth: 23, maxFrontier: 75621, known: 100005, maxTokens: 25, nbDeadlocks: 0
[DFS]99578 states, PT0.541S, maxWidth: 16, maxFrontier: 465, known: 100000, maxTokens: 21, nbDeadlocks: 0
Flow completion Exploration
[BFS]25668 states, PT1.161S, maxWidth: 21, maxFrontier: 74332, known: 100000, maxTokens: 24, nbDeadlocks: 0
[DFS]99721 states, PT1.814S, maxWidth: 20, maxFrontier: 344, known: 100000, maxTokens: 24, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]25668 states, PT1.23S, maxWidth: 21, maxFrontier: 74332, known: 100000, maxTokens: 24, nbDeadlocks: 0
[DFS]99721 states, PT1.701S, maxWidth: 20, maxFrontier: 344, known: 100000, maxTokens: 24, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]27208 states, PT1.363S, maxWidth: 21, maxFrontier: 72792, known: 100000, maxTokens: 24
[DFS]99714 states, PT1.773S, maxWidth: 20, maxFrontier: 344, known: 100000, maxTokens: 24

Exploring with "process 1" as the root process ...
callActivity 22 has no callable process (handled as a task)
callActivity 23 has no callable process (handled as a task)
Regular Exploration
[BFS]4971 states, PT0.733S, maxWidth: 138, maxFrontier: 95074, known: 100045, maxTokens: 16+128, nbDeadlocks: 0
[DFS]86954 states, PT17.153S, maxWidth: 83, maxFrontier: 13119, known: 100000, maxTokens: 16+396, nbDeadlocks: 1
Flow completion Exploration
[BFS]76613 states, PT43.108S, maxWidth: 17, maxFrontier: 23399, known: 100001, maxTokens: 16+425, nbDeadlocks: 0
[DFS]99864 states, PT10.794S, maxWidth: 15, maxFrontier: 209, known: 100000, maxTokens: 16+425, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
callActivity 22 has no callable process (handled as a task)
callActivity 23 has no callable process (handled as a task)
[BFS]16385 states, PT0.101S, maxWidth: 14, maxFrontier: 3628, known: 16385, maxTokens: 16, nbDeadlocks: 1
[DFS]16385 states, PT0.129S, maxWidth: 14, maxFrontier: 92, known: 16385, maxTokens: 16, nbDeadlocks: 1
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]16249 states, PT0.102S, maxWidth: 14, maxFrontier: 3594, known: 16249, maxTokens: 16
[DFS]16249 states, PT0.099S, maxWidth: 14, maxFrontier: 92, known: 16249, maxTokens: 16

Exploring with "process 3" as the root process ...
Regular Exploration
[BFS]28243 states, PT0.419S, maxWidth: 22, maxFrontier: 71758, known: 100001, maxTokens: 22, nbDeadlocks: 0
[DFS]99829 states, PT0.709S, maxWidth: 22, maxFrontier: 232, known: 100000, maxTokens: 22, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 22, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 22, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 22, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 22, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 22
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 22

Exploring with "process 8" as the root process ...
Regular Exploration
[BFS]5335 states, PT0.563S, maxWidth: 57, maxFrontier: 94680, known: 100015, maxTokens: 57, nbDeadlocks: 0
[DFS]98364 states, PT1.462S, maxWidth: 57, maxFrontier: 1679, known: 100002, maxTokens: 57, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 57, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 57, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 57, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 57, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 57
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 57

Exploring with "process 11" as the root process ...
Regular Exploration
[BFS]12 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 12, maxTokens: 1, nbDeadlocks: 0
[DFS]12 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 12, maxTokens: 1, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 1

Exploring with "process 13" as the root process ...
Regular Exploration
[BFS]17284 states, PT0.45S, maxWidth: 28, maxFrontier: 82720, known: 100004, maxTokens: 28, nbDeadlocks: 0
[DFS]99649 states, PT0.826S, maxWidth: 28, maxFrontier: 403, known: 100000, maxTokens: 28, nbDeadlocks: 0
Flow completion Exploration
[BFS]5 states, PT0.001S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 28, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 28, nbDeadlocks: 0
Flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 28, nbDeadlocks: 0
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 28, nbDeadlocks: 0
Amnesic, flow completion Exploration & handling all CallActivity as Task
[BFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 28
[DFS]5 states, PT0S, maxWidth: 1, maxFrontier: 1, known: 5, maxTokens: 28
*/
