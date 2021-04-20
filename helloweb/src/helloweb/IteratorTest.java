package helloweb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
public class IteratorTest {

	public static void main(String[] args) {

		long start = 0;
		long end = 0;

		int inRoopSize = 100;
		int maxRoopSize = 10000000;


		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Double> result = new ArrayList<Double>();
		Double[] arr = new Double[10000000];

		for (int i = 0; i < maxRoopSize; i++)
			list.add(i);

		Enumeration e = Collections.enumeration(list);
		// Iterator 사용 시 실행 시간

		System.out.println("----Iterator 사용 시----");

		for (int idx = 0; idx < inRoopSize; idx++) {
			start = System.nanoTime(); // 시작시간
			Iterator<Integer> itr = new EnumerationIterator(e);

			while (itr.hasNext()) {
				list.get(itr.next());
			}

			end = System.nanoTime(); // 끝나는 시간
			result.add(((double) (end - start) / 1000000000));

		}



		for (int i = 0; i < inRoopSize; i++)

			arr[i] = result.get(i);



		for (int i = 0; i < inRoopSize; i++)

			for (int j = 0; j < inRoopSize; j++)

				if (arr[i] < arr[j]) {

					double temp = arr[j];

					arr[j] = arr[i];

					arr[i] = temp;

				}



		for (int i = 0; i < 10; i++)

			System.out.println((i + 1) + " : " + arr[i] + "(sec)");

		

		System.out.println();

		

		// Size 받아온 코드 실행 시간

		System.out.println("-----Size 받아오는 방법 사용 시-----");

		for (int idx = 0; idx < inRoopSize; idx++) {

			start = System.nanoTime();

			int size = list.size();

			for (int i = 0; i < size; i++) {

				list.get(i);

			}

			end = System.nanoTime();



			result.add(((double) (end - start) / 1000000000));

		}

		

		arr = new Double[10000000];

		for (int i = 0; i < inRoopSize; i++)

			arr[i] = result.get(i);



		for (int i = 0; i < inRoopSize; i++)

			for (int j = 0; j < inRoopSize; j++)

				if (arr[i] < arr[j]) {

					double temp = arr[j];

					arr[j] = arr[i];

					arr[i] = temp;

				}



		for (int i = 0; i < 10; i++)

			System.out.println((i + 1) + " : " + arr[i] + "(sec)");

	}

}

