package api.prog5.bookwel.service.AI.DataProcesser;

import api.prog5.bookwel.Generated;

@Generated
public interface TriUserSpecificDataProcesser<T, U, V, R> {
  R process(T data, U data2, V data3, String userId);
}
