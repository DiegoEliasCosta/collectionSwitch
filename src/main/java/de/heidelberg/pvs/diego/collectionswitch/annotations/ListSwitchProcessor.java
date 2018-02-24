package de.heidelberg.pvs.diego.collectionswitch.annotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class ListSwitchProcessor extends AbstractProcessor {

	private static final String SWITCH_SUFFIX = "_Switch";
	private static final String TARGET_STATEMENT_FORMAT = "target.%1$s = %2$s";
	private static final String CONST_PARAM_TARGET_NAME = "target";

	private static final char CHAR_DOT = '.';

	private Messager messager;
	private Types typesUtil;
	private Elements elementsUtil;
	private Filer filer;

	@Override
	public synchronized void init(ProcessingEnvironment env) {
		super.init(env);

		messager = env.getMessager();
		typesUtil = env.getTypeUtils();
		elementsUtil = env.getElementUtils();
		filer = env.getFiler();
	}

	@Override
	public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment roundEnv) {

		Map<String, List<AnnotatedCollectionElement>> annotatedLists = new LinkedHashMap<>();

		for (Element element : roundEnv.getElementsAnnotatedWith(ListSwitch_Old.class)) {

			// TODO: Add here a set of validations
			if (element.getKind() != ElementKind.FIELD) {
				// TODO: Send a message
			}

			AnnotatedCollectionElement annotatedList = new AnnotatedCollectionElement(element);

			String qualifier = annotatedList.getQualifiedClassName().toString();
			if (annotatedLists.get(qualifier) == null) {
				annotatedLists.put(qualifier, new ArrayList<AnnotatedCollectionElement>());
			}
			annotatedLists.get(qualifier).add(annotatedList);

		}

		if (annotatedLists.size() == 0) {
			return true;
		}

		try {
			for (Map.Entry<String, List<AnnotatedCollectionElement>> entry : annotatedLists.entrySet()) {
				MethodSpec constructor = createConstructor(entry.getValue());
				TypeSpec binder = createClass(getClassName(entry.getKey()), constructor);
				JavaFile javaFile = JavaFile.builder(getPackage(entry.getKey()), binder).build();
				javaFile.writeTo(filer);
			}

		} catch (IOException e) {
			messager.printMessage(Diagnostic.Kind.ERROR, "Error on creating java file");
		}

		return false;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return ImmutableSet.of(ListSwitch_Old.class.getCanonicalName());
//		return Sets.newHashSet(ListSwitch.class.getName());
	}

	private MethodSpec createConstructor(List<AnnotatedCollectionElement> randomElements) {
		AnnotatedCollectionElement firstElement = randomElements.get(0);
		MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameter(
				TypeName.get(firstElement.getElement().getEnclosingElement().asType()), CONST_PARAM_TARGET_NAME);
		for (int i = 0; i < randomElements.size(); i++) {
			addStatement(builder, randomElements.get(i));

		}
		return builder.build();
	}

	private void addStatement(MethodSpec.Builder builder, AnnotatedCollectionElement randomElement) {
		//builder.addStatement(String.format(TARGET_STATEMENT_FORMAT, randomElement.getElementName().toString(),
			//	randomElement.getRandomValue()));
	}

	private TypeSpec createClass(String className, MethodSpec constructor) {
		return TypeSpec.classBuilder(className + SWITCH_SUFFIX).addModifiers(Modifier.PUBLIC, Modifier.FINAL)
				.addMethod(constructor).build();
	}

	private String getPackage(String qualifier) {
		return qualifier.substring(0, qualifier.lastIndexOf(CHAR_DOT));
	}

	private String getClassName(String qualifier) {
		return qualifier.substring(qualifier.lastIndexOf(CHAR_DOT) + 1);
	}

}
