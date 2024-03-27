package br.com.ti365.tabelaFipe.service.util;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public abstract class ServiceUtil {
	
	public static <T> T getItemInListByCode(List<T> itemList, Function<T, String> getCode, String className, Scanner scanner) {
	    System.out.println("\n###############################################################"
	            + "\n Digite o código do " + className + " que deseja: ");
	    String itemCodeOption = scanner.nextLine();
	    for (T item : itemList) {
	        if (getCode.apply(item).equals(itemCodeOption)) {
	            return item;
	        }
	    }

	    System.out.println(className + " não encontrado!");
	    return getItemInListByCode(itemList, getCode, className, scanner);
	}

}
