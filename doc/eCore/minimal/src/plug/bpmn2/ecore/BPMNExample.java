package plug.bpmn2.ecore;

public class BPMNExample {

    public static void main(String[] args) {
        BPMNeCoreLoader loader = new BPMNeCore Loader();
        loader.loadModelFromFilePath(args[0]);
        String output = new BPMNModelPrinter().getString(loader.getDocumentRoot());
        System.out.println(output);
    }

}
