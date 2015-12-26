/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 */

package com.cabshare.server.business.gcm;

import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;

import com.cabshare.server.properties.Properties;

class GcmPacketExtension extends DefaultPacketExtension {
    String json;

    public GcmPacketExtension(String json) {
      super(Properties.GCM_ELEMENT_NAME, Properties.GCM_NAMESPACE);
      this.json = json;
    }

    public String getJson() {
      return json;
    }
    @Override
    public String toXML() {
      return String.format("<%s xmlns=\"%s\">%s</%s>", Properties.GCM_ELEMENT_NAME,
          Properties.GCM_NAMESPACE, json, Properties.GCM_ELEMENT_NAME);
    }

    public Message toPacket() {
      return new Message() {
        // Must override toXML() because it includes a <body>
        @Override
        public String toXML() {

          StringBuilder buf = new StringBuilder();
          buf.append("<message");
          if (getXmlns() != null) {
            buf.append(" xmlns=\"").append(getXmlns()).append("\"");
          }
          if (getLanguage() != null) {
            buf.append(" xml:lang=\"").append(getLanguage()).append("\"");
          }
          if (getPacketID() != null) {
            buf.append(" id=\"").append(getPacketID()).append("\"");
          }
          if (getTo() != null) {
            buf.append(" to=\"").append(StringUtils.escapeForXML(getTo())).append("\"");
          }
          if (getFrom() != null) {
            buf.append(" from=\"").append(StringUtils.escapeForXML(getFrom())).append("\"");
          }
          buf.append(">");
          buf.append(GcmPacketExtension.this.toXML());
          buf.append("</message>");
          return buf.toString();
        }
      };
    }
  }