package io.rebolt.http.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.MediaType;
import io.rebolt.core.utils.JsonUtil;
import io.rebolt.core.utils.ObjectUtil;

/**
 * {@link Void} to {@link JsonNode} 컨버터
 *
 * Accept: "application/json;charset=utf-8"
 */
public class VoidToJsonConverter implements BytesConverter<Void, JsonNode> {
  @Override
  public byte[] convertRequest(Void aVoid) {
    return null;
  }

  @Override
  public JsonNode convertResponse(byte[] rawResponse) {
    return !ObjectUtil.isNull(rawResponse) ? JsonUtil.read(new String(rawResponse)) : null;
  }

  @Override
  public String getContentType() {
    return null;
  }

  @Override
  public String getAccept() {
    return MediaType.JSON_UTF_8.toString();
  }
}
