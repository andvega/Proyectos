/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnicas.analizador;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tecnicas.entity.Asuntos;
import tecnicas.entity.CasosRetroalim;
import tecnicas.entity.CasosRetroalimDet;
import tecnicas.entity.Requerimientos;
import tecnicas.manager.ManagerAsuntos;
import tecnicas.manager.ManagerCasosRetroalim;
import tecnicas.manager.ManagerCasosRetroalimDet;
import tecnicas.ontologia.AplicarMatriz;
import tecnicas.ontologia.AplicarOntologia;

/**
 *
 * @author ANDREA
 */
public class AplicarAnalizador {

    private CasosRetroalimDet[] casosRetroalimDet;
    private CasosRetroalim[] casosRetroalims;
    private String[] reqAnalizados;
    private String casosRetroAlim;

    public AplicarAnalizador() {
    }

    public void aplicar(String[] requerimientos) {

        try {

            /**
             * ************************************************************************************************
             */
            /* Analizador
             /***************************************************************************************************/
            ///String grammar = args.length > 0 ? args[0] : "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String[] options = {"-maxLength", "80", "-retainTmpSubcategories"};
            LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
            TreebankLanguagePack tlp = lp.getOp().langpack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

            reqAnalizados = new String[requerimientos.length];

            Iterable<List<? extends HasWord>> sentences;

            for (int r = 0; r < requerimientos.length; r++) {

                String sent2 = requerimientos[r] != null ? requerimientos[r] : "";

                Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
                List<? extends HasWord> sentence2 = toke.tokenize();
                List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
                tmp.add(sentence2);
                sentences = tmp;

                for (List<? extends HasWord> sentence : sentences) {
                    Tree parse = lp.parse(sentence);

                    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
                    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

                    reqAnalizados[r] = parse.taggedYield().toString();

                }

            }

        } catch (Exception ex) {
            Logger.getLogger(AplicarAnalizador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String[] aplicar(List<String> requerimientos) {

        try {

            /**
             * ************************************************************************************************
             */
            /* Analizador
             /***************************************************************************************************/
            ///String grammar = args.length > 0 ? args[0] : "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String[] options = {"-maxLength", "80", "-retainTmpSubcategories"};
            LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
            TreebankLanguagePack tlp = lp.getOp().langpack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
                   

            reqAnalizados = new String[requerimientos.size()];

            Iterable<List<? extends HasWord>> sentences;

            for (int r = 0; r < requerimientos.size(); r++) {

                String sent2 = requerimientos.get(r) != null ? requerimientos.get(r) : "";

                Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
                List<? extends HasWord> sentence2 = toke.tokenize();
                List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
                tmp.add(sentence2);
                sentences = tmp;
                
                List<CoreLabel> rawWords = Sentence.toCoreLabelList(sent2);
                Tree parse2 = lp.apply(rawWords);
                parse2.pennPrint();
               
                
                for (List<? extends HasWord> sentence : sentences) {
                    
                    Tree parse = lp.parse(sentence);
                    
                    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
                    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

                    reqAnalizados[r] = parse.taggedYield().toString();

                }

            }

        } catch (Exception ex) {
            Logger.getLogger(AplicarAnalizador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reqAnalizados;

    }

    public List<Requerimientos> aplicarList(List<String> requerimientos) {

        List<Requerimientos> lista = new ArrayList<Requerimientos>(requerimientos.size());

        try {
            /**
             * ************************************************************************************************
             */
            /* Analizador
             /***************************************************************************************************/
            ///String grammar = args.length > 0 ? args[0] : "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String[] options = {"-maxLength", "80", "-retainTmpSubcategories"};
            LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
            TreebankLanguagePack tlp = lp.getOp().langpack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

            Iterable<List<? extends HasWord>> sentences;
            Requerimientos auxiliar = new Requerimientos();

            for (int r = 0; r < requerimientos.size(); r++) {

                String sent2 = requerimientos.get(r) != null ? requerimientos.get(r) : "";

                Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
                List<? extends HasWord> sentence2 = toke.tokenize();
                List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
                tmp.add(sentence2);
                sentences = tmp;

                for (List<? extends HasWord> sentence : sentences) {
                    Tree parse = lp.parse(sentence);

                    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
                    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

                    auxiliar = new Requerimientos(r, parse.taggedYield().toString());
                    lista.add(r, auxiliar);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AplicarAnalizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public void analizador(AplicarOntologia ontologia, JenaOWLModel owlModelJ, ArrayList<String> requerimientos) {

        try {
            /**
             * ************************************************************************************************
             */
            /* Analizador
             /***************************************************************************************************/
            ///String grammar = args.length > 0 ? args[0] : "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String[] options = {"-maxLength", "80", "-retainTmpSubcategories"};
            LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
            TreebankLanguagePack tlp = lp.getOp().langpack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

            Iterable<List<? extends HasWord>> sentences;

            for (int r = 0; r < requerimientos.size(); r++) {

                String sent2 = requerimientos.get(r) != null ? requerimientos.get(r) : "";

                Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
                List<? extends HasWord> sentence2 = toke.tokenize();
                List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
                tmp.add(sentence2);
                sentences = tmp;

                for (List<? extends HasWord> sentence : sentences) {
                    Tree parse = lp.parse(sentence);

                    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
                    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

                    System.out.println();
                    System.out.println(parse.taggedYield());

                    /**
                     * ********************* Carga de conceptos
                     * ***********************
                     */
                    /**
                     * ********************* Verificamos si ya existe el
                     * individuo ***********************
                     */
                    OWLIndividual nuevoConcepto = ontologia.ejecutarBusquedaConcepts(owlModelJ, ontologia.getConceptos(), sent2);

                    /**
                     * ********************* Carga de conceptos
                     * ***********************
                     */
                    if (nuevoConcepto == null) {
                        nuevoConcepto = ontologia.getConceptos().createOWLIndividual(parse.toString());
                        nuevoConcepto.setComment(sent2);
                        nuevoConcepto.setProtegeType(ontologia.getConceptos());
                        nuevoConcepto.setVisible(true);

                        /**
                         * ********************* Casos de retroalimentación
                         * ***********************
                         */
                        int t = 0;
                        while (t < parse.taggedYield().size()) {

                            obtenerCasosRetroalim();
                            /**
                             * ********************* Casos de retroalimentación
                             * ***********************
                             */
                            int cantMayorTrue = 0;
                            int fin = 0;
                            String nombreIndividuo = "";
                            String nombreAux = "";
                            for (int c = 0; c < casosRetroalims.length; c++) {

                                /**
                                 * ********************* Casos de
                                 * retroalimentación ***********************
                                 */
                                casosRetroalimDet = new ManagerCasosRetroalimDet().findByIdCab(casosRetroalims[c].getId());
                                int cantTrue = 0;
                                boolean coincide = true;
                                for (int i = 0; i < casosRetroalimDet.length; i++) {
                                    /**
                                     * comparación con casos de
                                     * retroalimentación
                                     *
                                     */
                                    String posicion = casosRetroalimDet[i].getPosicion();
                                    String componente = casosRetroalimDet[i].getComponente();
                                    int tPosicion = t + Integer.parseInt(posicion);

                                    String individuoAux = "";

                                    if (tPosicion < parse.taggedYield().size()) {

                                        individuoAux = parse.taggedYield().get(tPosicion).value();

                                        if (tPosicion < parse.taggedYield().size() && parse.taggedYield().get(tPosicion).tag() != null
                                                && parse.taggedYield().get(tPosicion).tag().equals(componente)
                                                && !individuoAux.trim().toUpperCase().equals("system".trim().toUpperCase())
                                                && !individuoAux.toUpperCase().equals("Systems".toUpperCase())) {

                                            coincide = coincide || true;
                                            nombreAux = nombreAux + parse.taggedYield().get(tPosicion).value().toString() + " ";
                                            cantTrue++;

                                        } else {
                                            coincide = coincide && false;
                                        }

                                        if (coincide == true && i + 1 < casosRetroalimDet.length && casosRetroalimDet[i].getPosicion().equals(casosRetroalimDet[i + 1].getPosicion())) {
                                            i++;
                                        }

                                        if (coincide == false && i + 1 < casosRetroalimDet.length && !casosRetroalimDet[i].getPosicion().equals(casosRetroalimDet[i + 1].getPosicion())) {
                                            i = casosRetroalimDet.length + 1;
                                            nombreAux = "";
                                            cantTrue = 0;

                                        } else if (coincide == false && i + 1 == casosRetroalimDet.length) {
                                            nombreAux = "";
                                            cantTrue = 0;
                                        }

                                    }//Considerar el caso de que no coincide el tag con el componente despues de recorrer todo el detalle, 
                                    //entonces no es un asunto
                                }
                                if (coincide && cantTrue > cantMayorTrue) {
                                    cantMayorTrue = cantTrue;
                                    nombreIndividuo = nombreAux;
                                    nombreAux = "";
                                    cantTrue = 0;
                                } else if (coincide && cantTrue <= cantMayorTrue) {
                                    nombreAux = "";
                                    cantTrue = 0;
                                }

                            }
                            //fin = t + cantMayorTrue;
                            if (cantMayorTrue > 1) {
                                t += cantMayorTrue;
                            } else {
                                nombreAux = "";
                                t++;
                            }

                            if (!nombreIndividuo.equals("") && !nombreIndividuo.trim().toUpperCase().equals("system".trim().toUpperCase())
                                    && !nombreIndividuo.toUpperCase().equals("Systems".toUpperCase())) {
                                nombreIndividuo = nombreIndividuo.substring(0, nombreIndividuo.length() - 1);

                                /**
                                 * ********************* Verificamos si ya
                                 * existe el individuo ***********************
                                 */
                                OWLIndividual nuevoIndividuo = ontologia.ejecutarBusqueda(owlModelJ, ontologia.getObjetos(), nombreIndividuo);

                                if (nuevoIndividuo == null) {
                                    nuevoIndividuo = ontologia.getObjetos().createOWLIndividual(nombreIndividuo);
                                    nuevoIndividuo.setComment(nombreIndividuo);
                                    nuevoIndividuo.setProtegeType(ontologia.getObjetos());
                                    nuevoIndividuo.setVisible(true);

                                }

                                boolean existe = ontologia.ejecutarBusquedaRelaciones(nuevoConcepto, ontologia.getConceptProperty(), nuevoIndividuo);

                                if (!existe) {
                                    nuevoConcepto.addPropertyValue(ontologia.getConceptProperty(), nuevoIndividuo);
                                }

                                System.out.println("Nuevo Individuo: " + nuevoIndividuo.getName());
                            }

                        }

                    }

                }

            }
            /**
             * **********************Se guarda la
             * ontologia***********************************************
             */
            ontologia.guardarOntologia();
        } catch (Exception ex) {
            Logger.getLogger(AplicarAnalizador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void analizador(List<String> requerimientos) {

        try {
            
            AplicarMatriz apM = new AplicarMatriz();
            
            /**
             * ************************************************************************************************
             */
            /* Analizador
             /***************************************************************************************************/
            //String grammar = args.length > 0 ? args[0] : "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
           // String grammar = "edu.stanford.nlp.process.WhitespaceTokenizer -tokenizerMethod newCoreLabelTokenizerFactory  edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String[] options = {"-maxLength", "80", "-retainTmpSubcategories"};
            LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
            TreebankLanguagePack tlp = lp.getOp().langpack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
            
            //CoreNLP
//            Properties props = new Properties();
//            props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
//            //props.setProperty("annotators", "pos, lemma");
//            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

            Iterable<List<? extends HasWord>> sentences;

            for (int r = 0; r < requerimientos.size(); r++) {

                String sent2 = requerimientos.get(r) != null ? requerimientos.get(r) : "";

                Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
                List<? extends HasWord> sentence2 = toke.tokenize();
                List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
                tmp.add(sentence2);
                sentences = tmp;

                for (List<? extends HasWord> sentence : sentences) {
                    Tree parse = lp.parse(sentence);
                    
                    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
                    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
                    

                    System.out.println();
                    System.out.println(parse.taggedYield());

                    /**
                     * ********************* Carga de conceptos
                     * ***********************
                     */
                    /**
                     * ********************* Verificamos si ya existe el
                     * individuo ***********************
                     */
                    Requerimientos nuevoConcepto = apM.ejecutarBusquedaConcepts(sent2);

                    /**
                     * ********************* Carga de conceptos
                     * ***********************
                     */
                    if (nuevoConcepto != null) {
                        //nuevoConcepto = new Requerimientos();
                        //nuevoConcepto.setRequerimiento(sent2);
                        
                        /**
                         * ********************* Casos de retroalimentación
                         * ***********************
                         */
                        int t = 0;
                        while (t < parse.taggedYield().size()) {

                            obtenerCasosRetroalim();
                            /**
                             * ********************* Casos de retroalimentación
                             * ***********************
                             */
                            int cantMayorTrue = 0;
                            int fin = 0;
                            String nombreIndividuo = "";
                            String nombreAux = "";
                            for (int c = 0; c < casosRetroalims.length; c++) {

                                /**
                                 * ********************* Casos de
                                 * retroalimentación ***********************
                                 */
                                casosRetroalimDet = new ManagerCasosRetroalimDet().findByIdCab(casosRetroalims[c].getId());
                                int cantTrue = 0;
                                boolean coincide = true;
                                for (int i = 0; i < casosRetroalimDet.length; i++) {
                                    /**
                                     * comparación con casos de
                                     * retroalimentación
                                     *
                                     */
                                    String posicion = casosRetroalimDet[i].getPosicion();
                                    String componente = casosRetroalimDet[i].getComponente();
                                    int tPosicion = t + Integer.parseInt(posicion);

                                    String individuoAux = "";

                                    if (tPosicion < parse.taggedYield().size()) {

                                        individuoAux = parse.taggedYield().get(tPosicion).value();
                                        Document document = new Document(individuoAux);
                                        for (edu.stanford.nlp.simple.Sentence sente : document.sentences()) {
                                            sente.word(0);
                                            sente.lemma(0);
                                            individuoAux = sente.lemma(0);
                                        }
                                        //CoreNLP
//                                        PrintWriter xmlOut = null;
//                                        PrintWriter out = new PrintWriter(System.out);
//                                        Annotation annotation = new Annotation(individuoAux);
//        
//                                        pipeline.annotate(annotation);
//                                        pipeline.prettyPrint(annotation, out);
//                                        
//                                        if (xmlOut != null) {
//                                            pipeline.xmlPrint(annotation, xmlOut);
//                                        }
//                                        List<CoreMap> t = annotation.get(CoreAnnotations.SentencesAnnotation.class);
//                                        
//                                        edu.stanford.nlp.simple.Document document = new edu.stanford.nlp.simple.Document("data");
//                                        for(edu.stanford.nlp.simple.Sentence sente: document.sentences()){
//                                            sente.word(1);
//                                            sente.lemma(2);
//                                        }
//                                        
//                                        if (sentences2 != null && sentences2.size() > 0) {
//                                            CoreMap sentenceNLP = sentences2.get(0);
//                                            sentenceNLP.get(edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation.class);
//                                            out.println();
//                                            
//                                        }
                                      
                                        
                                                
                                        if (tPosicion < parse.taggedYield().size() && parse.taggedYield().get(tPosicion).tag() != null
                                                && parse.taggedYield().get(tPosicion).tag().equals(componente)
                                                && !individuoAux.trim().toUpperCase().equals("system".trim().toUpperCase())
                                                && !individuoAux.toUpperCase().equals("Systems".toUpperCase())) {

                                            coincide = coincide || true;
                                            //nombreAux = nombreAux + parse.taggedYield().get(tPosicion).value().toString() + " ";
                                            nombreAux = nombreAux + individuoAux + " ";
                                            cantTrue++;

                                        } else {
                                            coincide = coincide && false;
                                        }

                                        if (coincide == true && i + 1 < casosRetroalimDet.length && casosRetroalimDet[i].getPosicion().equals(casosRetroalimDet[i + 1].getPosicion())) {
                                            i++;
                                        }

                                        if (coincide == false && i + 1 < casosRetroalimDet.length && !casosRetroalimDet[i].getPosicion().equals(casosRetroalimDet[i + 1].getPosicion())) {
                                            i = casosRetroalimDet.length + 1;
                                            nombreAux = "";
                                            cantTrue = 0;

                                        } else if (coincide == false && i + 1 == casosRetroalimDet.length) {
                                            nombreAux = "";
                                            cantTrue = 0;
                                        }

                                    }//Considerar el caso de que no coincide el tag con el componente despues de recorrer todo el detalle, 
                                    //entonces no es un asunto
                                }
                                if (coincide && cantTrue > cantMayorTrue) {
                                    cantMayorTrue = cantTrue;
                                    nombreIndividuo = nombreAux;
                                    nombreAux = "";
                                    cantTrue = 0;
                                } else if (coincide && cantTrue <= cantMayorTrue) {
                                    nombreAux = "";
                                    cantTrue = 0;
                                }

                            }
                            //fin = t + cantMayorTrue;
                            if (cantMayorTrue > 1) {
                                t += cantMayorTrue;
                            } else {
                                nombreAux = "";
                                t++;
                            }

                            if (!nombreIndividuo.equals("") && !nombreIndividuo.trim().toUpperCase().equals("system".trim().toUpperCase())
                                    && !nombreIndividuo.toUpperCase().equals("Systems".toUpperCase())) {
                                nombreIndividuo = nombreIndividuo.substring(0, nombreIndividuo.length() - 1);

                                /**
                                 * ********************* Verificamos si ya
                                 * existe el individuo ***********************
                                 */
                                Asuntos nuevoIndividuo = apM.ejecutarBusqueda(nombreIndividuo);

                                if (nuevoIndividuo == null) {
                                    nuevoIndividuo = new Asuntos();
                                    nuevoIndividuo.setAsunto(nombreIndividuo);
                                    
                                    ManagerAsuntos mngAsuntos = new ManagerAsuntos();
                                    mngAsuntos.create(nuevoIndividuo);
                                    
                                }

                                boolean existe = apM.ejecutarBusquedaRelaciones(nuevoConcepto, nuevoIndividuo);

                                if (!existe) {
                                    apM.relacionarReqAsunto(nuevoConcepto, nuevoIndividuo);
                                }

                                System.out.println("Nuevo Individuo: " + nuevoIndividuo.getAsunto());
                            }

                        }

                    }

                }

            }
            /**
             * **********************Se guarda la
             * ontologia***********************************************
             */
            //ontologia.guardarOntologia();
        } catch (Exception ex) {
            Logger.getLogger(AplicarAnalizador.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public void obtenerCasosRetroalim() {
        try {
            ManagerCasosRetroalim mng = new ManagerCasosRetroalim();
            casosRetroalims = mng.getCasosCab();

        } catch (Exception ex) {
            Logger.getLogger(AplicarAnalizador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public CasosRetroalim[] getCasosRetroalims() throws Exception {
        return casosRetroalims;
    }

    public CasosRetroalimDet[] getCasosRetroalimDet() throws Exception {
        return casosRetroalimDet = new ManagerCasosRetroalimDet().getAll();
    }

    public void setCasosRetroalimDet(CasosRetroalimDet[] casosRetroalimDet) {
        this.casosRetroalimDet = casosRetroalimDet;
    }

    public String[] getReqAnalizados() {
        return reqAnalizados;
    }

    public void setReqAnalizados(String[] reqAnalizados) {
        this.reqAnalizados = reqAnalizados;
    }
}
