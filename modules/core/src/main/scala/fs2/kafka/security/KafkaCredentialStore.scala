/*
 * Copyright 2018-2023 OVO Energy Limited
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package fs2.kafka.security

sealed trait KafkaCredentialStore {
  def properties: Map[String, String]
}

object KafkaCredentialStore {
  final def fromPemStrings(
    caCertificate: String,
    clientPrivateKey: String,
    clientCertificate: String
  ): KafkaCredentialStore =
    new KafkaCredentialStore {
      override val properties: Map[String, String] =
        Map(
          "security.protocol" -> "SSL",
          "ssl.truststore.type" -> "PEM",
          "ssl.truststore.certificates" -> caCertificate.replace('\n', ' '),
          "ssl.keystore.type" -> "PEM",
          "ssl.keystore.key" -> clientPrivateKey.replace('\n', ' '),
          "ssl.keystore.certificate.chain" -> clientCertificate.replace('\n', ' ')
        )
    }
}
