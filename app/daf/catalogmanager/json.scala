/*
 * Copyright 2017 TEAM PER LA TRASFORMAZIONE DIGITALE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package daf.catalogmanager

import play.api.libs.json._

// Generic Key/Value pair object
case class KeyValue(key: String, value: String)
// Generic Key/Value pair object
case class VocKeyValueSubtheme(key: String, value: String, keyTheme: String, valueTheme: Option[String])
case class KeyValueArray(key: String, value: List[String])
case class MetaCatalog(dataschema: DatasetCatalog, operational: Operational, dcatapit: Dataset)
case class DatasetCatalog(avro: Avro, flatSchema: List[FlatSchema], kyloSchema: Option[String], encoding: Option[String])
case class Avro(namespace: String, `type`: String, name: String, aliases: Option[List[String]], fields: Option[List[Field]], separator: Option[String], property_hierarchy: Option[String])
case class Field(name: String, `type`: String)
case class FlatSchema(name: String, `type`: String, metadata: Option[Metadata])
case class Metadata(desc: Option[String], field_type: Option[String], required: Option[Int], semantics: Option[Semantic], format_std: Option[FormatStd], is_createdate: Option[Boolean], is_updatedate: Option[Boolean], key: Option[Boolean], personal: Option[Personal], title: Option[String], `type`: Option[String])
case class Personal(is_personal: Option[Boolean], cat: Option[String], processing: Option[String], is_analysis: Option[Boolean])
case class FormatStd(name: Option[String], param: Option[List[KeyValue]], conv: Option[List[KeyValueArray]])
case class FieldProfile(is_index: Option[Boolean], is_profile: Option[Boolean], standardization: Option[List[String]], entity_extr: Option[List[EntityExtraction]], validation: Option[List[String]])
case class EntityExtraction(name: String, param: Option[Seq[KeyValue]])
case class Lang(eng: Option[String], ita: Option[String])
case class Semantic(id: String, context: Option[String], predicate: Option[String], subject: Option[String], id_label: Option[String], context_label: Option[String], uri_voc: Option[String], property_hierarchy: Option[List[String]], rdf_object: Option[String], field_group: Option[String], uri_property: Option[String])
case class Constr(`type`: Option[String], param: Option[String])
case class Operational(theme: String, subtheme: String, logical_uri: Option[String], physical_uri: Option[String], is_std: Boolean, group_own: String, std_schema: Option[StdSchema], read_type: String, input_src: InputSrc, dataset_type: String, is_vocabulary: Option[Boolean], ext_opendata: Option[ExtOpenData], acl: Option[List[Acl]], file_type: Option[String], partitions: Option[List[Partitions]], dataset_proc: Option[DatasetProc], type_info: Option[TypeInfo], storage_info: Option[StorageInfo])
case class TypeInfo(dataset_type: String, sources: Option[List[String]], query_json: Option[String], query_sql: Option[String])
case class ExtOpenData(resourceId: String, name: String, url: String, resourceName: String, id: String, resourceUrl: String)
case class Acl(groupName: Option[String], groupType: Option[String], permission: Option[String])
case class Partitions(name: String, field: String, formula: String)
case class DatasetProc(dataset_type: String, cron: String, merge_strategy: String, scheduling_strategy: Option[String], partitions: Option[List[Partitions]])

case class InputSrc(sftp: Option[List[SourceSftp]], srv_pull: Option[List[SourceSrvPull]], srv_push: Option[List[SourceSrvPush]])
// Info for the ingestion source of type SFTP
case class SourceSftp(name: String, url: Option[String], username: Option[String], password: Option[String], param: Option[String])
// Info for the ingestion source of type pulling a service, that is we make a call to the specified url
case class SourceSrvPull(name: String, url: String, username: Option[String], password: Option[String], access_token: Option[String], param: Option[String])
case class StorageInfo(hdfs: Option[StorageHdfs])
case class StorageHdfs(name: String, path: Option[String], param: Option[String])
case class SourceSrvPush(name: String, url: String, username: Option[String], password: Option[String], access_token: Option[String], param: Option[String])
case class StdSchema(std_uri: String, fields_conv: List[ConversionField])
case class ConversionSchema(fields_conv: List[ConversionField], fields_custom: Option[List[CustomField]])
case class ConversionField(field_std: String, formula: String)
case class CustomField(name: String)
case class Error(code: Option[Int], message: String, fields: Option[String])
case class Success(message: String, fields: Option[String])
case class Dataset(alternate_identifier: Option[String], author: Option[String], frequency: Option[String], holder_identifier: Option[String], holder_name: Option[String], identifier: Option[String], license_id: Option[String], license_title: Option[String], modified: Option[String], name: String, notes: Option[String], owner_org: Option[String], publisher_identifier: Option[String], publisher_name: Option[String], relationships_as_object: Option[List[Relationship]], resources: Option[List[Resource]], theme: Option[String], title: Option[String], privatex: Option[Boolean])
case class Relationship(subject: Option[String], `object`: Option[String], `type`: Option[String], comment: Option[String])
case class Resource(cache_last_updated: Option[String], cache_url: Option[String], created: Option[String], datastore_active: Option[Boolean], description: Option[String], distribution_format: Option[String], format: Option[String], hash: Option[String], id: Option[String], last_modified: Option[String], mimetype: Option[String], mimetype_inner: Option[String], name: Option[String], package_id: Option[String], position: Option[Int], resource_type: Option[String], revision_id: Option[String], size: Option[Int], state: Option[String], url: Option[String])
case class Extra(key: Option[String], value: Option[String])
case class StdUris(label: Option[String], value: Option[String])
case class Token(token: Option[String])
case class User(name: Option[String], email: Option[String], password: Option[String], fullname: Option[String], about: Option[String])
case class AutocompRes(match_field: Option[String], match_displayed: Option[String], name: Option[String], title: Option[String])
case class Credentials(username: Option[String], password: Option[String])

object json {

  implicit lazy val KeyValueReads: Reads[KeyValue] = Reads[KeyValue] {
    json => JsSuccess(KeyValue((json \ "key").as[String], (json \ "value").as[String]))
  }
  implicit lazy val KeyValueWrites: Writes[KeyValue] = Writes[KeyValue] {
    o => JsObject(Seq("key" -> Json.toJson(o.key), "value" -> Json.toJson(o.value)).filter(_._2 != JsNull))
  }
  implicit lazy val VocKeyValueSubthemeReads: Reads[VocKeyValueSubtheme] = Reads[VocKeyValueSubtheme] {
    json => JsSuccess(VocKeyValueSubtheme((json \ "key").as[String], (json \ "value").as[String], (json \ "keyTheme").as[String], (json \ "valueTheme").asOpt[String]))
  }
  implicit lazy val VocKeyValueSubthemeWrites: Writes[VocKeyValueSubtheme] = Writes[VocKeyValueSubtheme] {
    o => JsObject(Seq("key" -> Json.toJson(o.key), "value" -> Json.toJson(o.value), "keyTheme" -> Json.toJson(o.keyTheme), "valueTheme" -> Json.toJson(o.valueTheme)).filter(_._2 != JsNull))
  }
  implicit lazy val MetaCatalogReads: Reads[MetaCatalog] = Reads[MetaCatalog] {
    json => JsSuccess(MetaCatalog((json \ "dataschema").as[DatasetCatalog], (json \ "operational").as[Operational], (json \ "dcatapit").as[Dataset]))
  }
  implicit lazy val MetaCatalogWrites: Writes[MetaCatalog] = Writes[MetaCatalog] {
    o => JsObject(Seq("dataschema" -> Json.toJson(o.dataschema), "operational" -> Json.toJson(o.operational), "dcatapit" -> Json.toJson(o.dcatapit)).filter(_._2 != JsNull))
  }
  implicit lazy val DatasetCatalogReads: Reads[DatasetCatalog] = Reads[DatasetCatalog] {
    json => JsSuccess(DatasetCatalog((json \ "avro").as[Avro], (json \ "flatSchema").as[List[FlatSchema]],(json \ "kyloSchema").asOpt[String], (json \ "encoding").asOpt[String]))
  }
  implicit lazy val DatasetCatalogWrites: Writes[DatasetCatalog] = Writes[DatasetCatalog] {
    o => JsObject(Seq("avro" -> Json.toJson(o.avro), "flatSchema" -> Json.toJson(o.flatSchema), "kyloSchema" -> Json.toJson(o.kyloSchema), "encoding" -> Json.toJson(o.encoding)).filter(_._2 != JsNull))
  }
  implicit lazy val AvroReads: Reads[Avro] = Reads[Avro] {
    json => JsSuccess(Avro((json \ "namespace").as[String], (json \ "type").as[String], (json \ "name").as[String], (json \ "aliases").asOpt[List[String]], (json \ "fields").asOpt[List[Field]], (json \ "separator").asOpt[String], (json \ "property_hierarchy").asOpt[String]))
  }
  implicit lazy val AvroWrites: Writes[Avro] = Writes[Avro] {
    o => JsObject(Seq("namespace" -> Json.toJson(o.namespace), "type" -> Json.toJson(o.`type`), "name" -> Json.toJson(o.name), "aliases" -> Json.toJson(o.aliases), "fields" -> Json.toJson(o.fields), "separator" -> Json.toJson(o.separator), "property_hierarchy" -> Json.toJson(o.property_hierarchy)).filter(_._2 != JsNull))
  }
  implicit lazy val FieldReads: Reads[Field] = Reads[Field] {
    json => JsSuccess(Field((json \ "name").as[String], (json \ "type").as[String]))
  }
  implicit lazy val FieldWrites: Writes[Field] = Writes[Field] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "type" -> Json.toJson(o.`type`)).filter(_._2 != JsNull))
  }
  implicit lazy val FlatSchemaReads: Reads[FlatSchema] = Reads[FlatSchema] {
    json => JsSuccess(FlatSchema((json \ "name").as[String], (json \ "type").as[String], (json \ "metadata").asOpt[Metadata]))
  }
  implicit lazy val FlatSchemaWrites: Writes[FlatSchema] = Writes[FlatSchema] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "type" -> Json.toJson(o.`type`), "metadata" -> Json.toJson(o.metadata)).filter(_._2 != JsNull))
  }
  implicit lazy val MetadataReads: Reads[Metadata] = Reads[Metadata] {
    json => JsSuccess(Metadata((json \ "desc").asOpt[String], (json \ "field_type").asOpt[String], (json \ "required").asOpt[Int], (json \ "semantics").asOpt[Semantic], (json \ "format_std").asOpt[FormatStd], (json \ "is_created").asOpt[Boolean], (json \ "is_updatedate").asOpt[Boolean], (json \ "key").asOpt[Boolean], (json \ "personal").asOpt[Personal], (json \ "title").asOpt[String], (json \ "`type`").asOpt[String]))
  }
  implicit lazy val MetadataWrites: Writes[Metadata] = Writes[Metadata] {
    o => JsObject(Seq("desc" -> Json.toJson(o.desc), "field_type" -> Json.toJson(o.field_type), "required" -> Json.toJson(o.required), "semantics" -> Json.toJson(o.semantics), "format_std" -> Json.toJson(o.format_std), "is_created" -> Json.toJson(o.is_createdate), "is_updatedate" -> Json.toJson(o.is_updatedate), "key" -> Json.toJson(o.key), "personal" -> Json.toJson(o.personal), "title" -> Json.toJson(o.title), "`type`" -> Json.toJson(o.`type`)).filter(_._2 != JsNull))
  }
  implicit lazy val FieldProfileReads: Reads[FieldProfile] = Reads[FieldProfile] {
    json => JsSuccess(FieldProfile((json \ "is_index").asOpt[Boolean], (json \ "is_profile").asOpt[Boolean], (json \ "standardization").asOpt[List[String]], (json \ "entity_extr").asOpt[List[EntityExtraction]], (json \ "validation").asOpt[List[String]]))
  }
  implicit lazy val FieldProfileWrites: Writes[FieldProfile] = Writes[FieldProfile] {
    o => JsObject(Seq("is_index" -> Json.toJson(o.is_index), "is_profile" -> Json.toJson(o.is_profile), "standardization" -> Json.toJson(o.standardization), "entity_extr" -> Json.toJson(o.entity_extr), "validation" -> Json.toJson(o.validation)))
  }
  implicit lazy val EntityExtractionReads: Reads[EntityExtraction] = Reads[EntityExtraction] {
    json => JsSuccess(EntityExtraction((json \ "name").as[String], (json \ "param").asOpt[Seq[KeyValue]]))
  }
  implicit lazy val EntityExtractionWrites: Writes[EntityExtraction] = Writes[EntityExtraction] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "param" -> Json.toJson(o.param)))
  }
  implicit lazy val FormatStdReads: Reads[FormatStd] = Reads[FormatStd] {
    json => JsSuccess(FormatStd((json \ "name").asOpt[String], (json \ "param").asOpt[List[KeyValue]], (json \ "conv").asOpt[List[KeyValueArray]]))
  }
  implicit lazy val FormatStdWrites: Writes[FormatStd] = Writes[FormatStd] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "param" -> Json.toJson(o.param), "conv" -> Json.toJson(o.conv)))
  }
  implicit lazy val PersonalReads: Reads[Personal] = Reads[Personal] {
    json => JsSuccess(Personal((json \ "is_personal").asOpt[Boolean], (json \ "cat").asOpt[String], (json \ "processing").asOpt[String], (json \ "is_analysis").asOpt[Boolean]))
  }
  implicit lazy val PersonalWrites: Writes[Personal] = Writes[Personal] {
    o => JsObject(Seq("is_personal" -> Json.toJson(o.is_personal), "cat" -> Json.toJson(o.cat), "processing" -> Json.toJson(o.processing), "is_analysis" -> Json.toJson(o.is_analysis)))
  }
  implicit lazy val KeyValueArrayReads: Reads[KeyValueArray] = Reads[KeyValueArray] {
    json => JsSuccess(KeyValueArray((json \ "key").as[String], (json \ "value").as[List[String]]))
  }
  implicit lazy val KeyValueArrayWrites: Writes[KeyValueArray] = Writes[KeyValueArray] {
    o => JsObject(Seq("key" -> Json.toJson(o.key), "value" -> Json.toJson(o.value)))
  }
  implicit lazy val LangReads: Reads[Lang] = Reads[Lang] {
    json => JsSuccess(Lang((json \ "eng").asOpt[String], (json \ "ita").asOpt[String]))
  }
  implicit lazy val LangWrites: Writes[Lang] = Writes[Lang] {
    o => JsObject(Seq("eng" -> Json.toJson(o.eng), "ita" -> Json.toJson(o.ita)).filter(_._2 != JsNull))
  }
  implicit lazy val SemanticReads: Reads[Semantic] = Reads[Semantic] {
    json => JsSuccess(Semantic((json \ "id").as[String], (json \ "context").asOpt[String], (json \ "predicate").asOpt[String], (json \ "subject").asOpt[String], (json \ "id_label").asOpt[String], (json \ "context_label").asOpt[String], (json \ "uri_voc").asOpt[String], (json \ "property_hierarchy").asOpt[List[String]], (json \ "rdf_object").asOpt[String], (json \ "field_group").asOpt[String], (json \ "uri_property").asOpt[String]))
  }
  implicit lazy val SemanticWrites: Writes[Semantic] = Writes[Semantic] {
    o => JsObject(Seq("id" -> Json.toJson(o.id), "context" -> Json.toJson(o.context), "predicate" -> Json.toJson(o.predicate), "subject" -> Json.toJson(o.subject), "id_label" -> Json.toJson(o.id_label), "context_label" -> Json.toJson(o.context_label), "uri_voc" -> Json.toJson(o.uri_voc), "property_hierarchy" -> Json.toJson(o.property_hierarchy), "rdf_object" -> Json.toJson(o.rdf_object), "field_group" -> Json.toJson(o.field_group), "uri_property" -> Json.toJson(o.uri_property)).filter(_._2 != JsNull))
  }
  implicit lazy val ConstrReads: Reads[Constr] = Reads[Constr] {
    json => JsSuccess(Constr((json \ "type").asOpt[String], (json \ "param").asOpt[String]))
  }
  implicit lazy val ConstrWrites: Writes[Constr] = Writes[Constr] {
    o => JsObject(Seq("type" -> Json.toJson(o.`type`), "param" -> Json.toJson(o.param)).filter(_._2 != JsNull))
  }
  implicit lazy val StorageHdfsReads: Reads[StorageHdfs] = Reads[StorageHdfs] {
    json => JsSuccess(StorageHdfs((json \ "name").as[String], (json \ "path").asOpt[String], (json \ "param").asOpt[String]))
  }
  implicit lazy val StorageHdfsWrites: Writes[StorageHdfs] = Writes[StorageHdfs] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "path" -> Json.toJson(o.path), "param" -> Json.toJson(o.param)).filter(_._2 != JsNull))
  }
  implicit lazy val StorageInfoReads: Reads[StorageInfo] = Reads[StorageInfo] {
    json => JsSuccess(StorageInfo((json \ "hdfs").asOpt[StorageHdfs]))
  }
  implicit lazy val StorageInfoWrites: Writes[StorageInfo] = Writes[StorageInfo]{
    o => JsObject(Seq("hdfs" -> Json.toJson(o.hdfs)).filter(_._2 != JsNull))
  }
  implicit lazy val OperationalReads: Reads[Operational] = Reads[Operational] {
    json => JsSuccess(Operational((json \ "theme").as[String], (json \ "subtheme").as[String], (json \ "logical_uri").asOpt[String], (json \ "physical_uri").asOpt[String], (json \ "is_std").as[Boolean], (json \ "group_own").as[String], (json \ "std_schema").asOpt[StdSchema], (json \ "read_type").as[String], (json \ "input_src").as[InputSrc], (json \ "dataset_type").as[String], (json \ "is_vocabulary").asOpt[Boolean], (json \ "ext_opendata").asOpt[ExtOpenData], (json \ "acl").asOpt[List[Acl]], (json \ "file_type").asOpt[String], (json \ "partitions").asOpt[List[Partitions]], (json \ "dataset_proc").asOpt[DatasetProc], (json \ "type_info").asOpt[TypeInfo], (json \ "storage_info").asOpt[StorageInfo]))
  }
  implicit lazy val OperationalWrites: Writes[Operational] = Writes[Operational] {
    o => JsObject(Seq("theme" -> Json.toJson(o.theme), "subtheme" -> Json.toJson(o.subtheme), "logical_uri" -> Json.toJson(o.logical_uri), "physical_uri" -> Json.toJson(o.physical_uri), "is_std" -> Json.toJson(o.is_std), "group_own" -> Json.toJson(o.group_own), "std_schema" -> Json.toJson(o.std_schema), "read_type" -> Json.toJson(o.read_type), "input_src" -> Json.toJson(o.input_src), "dataset_type" -> Json.toJson(o.dataset_type), "is_vocabulary" -> Json.toJson(o.is_vocabulary), "ext_opendata" -> Json.toJson(o.ext_opendata), "acl" -> Json.toJson(o.acl), "file_type" -> Json.toJson(o.file_type), "partitions" -> Json.toJson(o.partitions), "dataset_proc" -> Json.toJson(o.dataset_proc), "type_info" -> Json.toJson(o.type_info), "storage_info" -> Json.toJson(o.storage_info)).filter(_._2 != JsNull))
  }
  implicit lazy val TypeInfoReads: Reads[TypeInfo] = Reads[TypeInfo] {
    json => JsSuccess(TypeInfo((json \ "dataset_type").as[String], (json \ "sources").asOpt[List[String]], (json \ "query_json").asOpt[String], (json \ "query_sql").asOpt[String]))
  }
  implicit lazy val typeInfoWrites: Writes[TypeInfo] = Writes[TypeInfo] {
    o => JsObject(Seq("dataset_type" -> Json.toJson(o.dataset_type), "sources" -> Json.toJson(o.sources), "query_json" -> Json.toJson(o.query_json), "query_sql" -> Json.toJson(o.query_sql)).filter(_._2 != JsNull))
  }
  implicit lazy val ExtOpenDataReads: Reads[ExtOpenData] = Reads[ExtOpenData] {
    json => JsSuccess(ExtOpenData((json \ "resourceId").as[String], (json \ "name").as[String], (json \ "url").as[String], (json \ "resourceName").as[String], (json \ "id").as[String], (json \ "resourceUrl").as[String]))
  }
  implicit lazy val ExtOpenDataWrites: Writes[ExtOpenData] = Writes[ExtOpenData] {
    o => JsObject(Seq("resourceId" -> Json.toJson(o.resourceId), "name" -> Json.toJson(o.name), "url" -> Json.toJson(o.url), "resourceName" -> Json.toJson(o.resourceName), "id" -> Json.toJson(o.id), "resourceUrl" -> Json.toJson(o.resourceUrl)))
  }
  implicit lazy val AclReads: Reads[Acl] = Reads[Acl] {
    json => JsSuccess(Acl((json \ "groupName").asOpt[String], (json \ "groupType").asOpt[String], (json \ "permission").asOpt[String]))
  }
  implicit lazy val AclWrites: Writes[Acl] = Writes[Acl] {
    o => JsObject(Seq("groupName" -> Json.toJson(o.groupName), "groupType" -> Json.toJson(o.groupType), "permission" -> Json.toJson(o.permission)))
  }
  implicit lazy val PartitionsReads: Reads[Partitions] = Reads[Partitions] {
    json => JsSuccess(Partitions((json \ "name").as[String], (json \ "field").as[String], (json \ "formula").as[String]))
  }
  implicit lazy val PartitionsWrites: Writes[Partitions] = Writes[Partitions] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "field" -> Json.toJson(o.field), "formula" -> Json.toJson(o.formula)))
  }
  implicit lazy val DatasetProcReads: Reads[DatasetProc] = Reads[DatasetProc] {
    json => JsSuccess(DatasetProc((json \ "dataset_type").as[String], (json \ "cron").as[String], (json \ "merge_strategy").as[String], (json \ "scheduling_strategy").asOpt[String], (json \ "partitions").asOpt[List[Partitions]]))
  }
  implicit lazy val DatasetProcWrites: Writes[DatasetProc] = Writes[DatasetProc] {
    o => JsObject(Seq("dataset_type" -> Json.toJson(o.dataset_type), "cron" -> Json.toJson(o.cron), "merge_strategy" -> Json.toJson(o.merge_strategy), "scheduling_strategy" -> Json.toJson(o.scheduling_strategy), "partitions" -> Json.toJson(o.partitions)))
  }
  implicit lazy val InputSrcReads: Reads[InputSrc] = Reads[InputSrc] {
    json => JsSuccess(InputSrc((json \ "sftp").asOpt[List[SourceSftp]], (json \ "srv_pull").asOpt[List[SourceSrvPull]], (json \ "srv_push").asOpt[List[SourceSrvPush]]))
  }
  implicit lazy val InputSrcWrites: Writes[InputSrc] = Writes[InputSrc] {
    o => JsObject(Seq("sftp" -> Json.toJson(o.sftp), "srv_pull" -> Json.toJson(o.srv_pull), "srv_push" -> Json.toJson(o.srv_push)))
  }
  implicit lazy val SourceSftpReads: Reads[SourceSftp] = Reads[SourceSftp] {
    json => JsSuccess(SourceSftp((json \ "name").as[String], (json \ "url").asOpt[String], (json \ "username").asOpt[String], (json \ "password").asOpt[String], (json \ "param").asOpt[String]))
  }
  implicit lazy val SourceSftpWrites: Writes[SourceSftp] = Writes[SourceSftp] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "url" -> Json.toJson(o.url), "username" -> Json.toJson(o.username), "password" -> Json.toJson(o.password), "param" -> Json.toJson(o.param)).filter(_._2 != JsNull))
  }
  implicit lazy val SourceSrvPullReads: Reads[SourceSrvPull] = Reads[SourceSrvPull] {
    json => JsSuccess(SourceSrvPull((json \ "name").as[String], (json \ "url").as[String], (json \ "username").asOpt[String], (json \ "password").asOpt[String], (json \ "access_token").asOpt[String], (json \ "param").asOpt[String]))
  }
  implicit lazy val SourceSrvPullWrites: Writes[SourceSrvPull] = Writes[SourceSrvPull] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "url" -> Json.toJson(o.url), "username" -> Json.toJson(o.username), "password" -> Json.toJson(o.password), "access_token" -> Json.toJson(o.access_token), "param" -> Json.toJson(o.param)).filter(_._2 != JsNull))
  }
  implicit lazy val SourceSrvPushReads: Reads[SourceSrvPush] = Reads[SourceSrvPush] {
    json => JsSuccess(SourceSrvPush((json \ "name").as[String], (json \ "url").as[String], (json \ "username").asOpt[String], (json \ "password").asOpt[String], (json \ "access_token").asOpt[String], (json \ "param").asOpt[String]))
  }
  implicit lazy val SourceSrvPushWrites: Writes[SourceSrvPush] = Writes[SourceSrvPush] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "url" -> Json.toJson(o.url), "username" -> Json.toJson(o.username), "password" -> Json.toJson(o.password), "access_token" -> Json.toJson(o.access_token), "param" -> Json.toJson(o.param)).filter(_._2 != JsNull))
  }
  implicit lazy val StdSchemaReads: Reads[StdSchema] = Reads[StdSchema] {
    json => JsSuccess(StdSchema((json \ "std_uri").as[String], (json \ "fields_conv").as[List[ConversionField]]))
  }
  implicit lazy val StdSchemaWrites: Writes[StdSchema] = Writes[StdSchema] {
    o => JsObject(Seq("std_uri" -> Json.toJson(o.std_uri), "fields_conv" -> Json.toJson(o.fields_conv)).filter(_._2 != JsNull))
  }
  implicit lazy val ConversionSchemaReads: Reads[ConversionSchema] = Reads[ConversionSchema] {
    json => JsSuccess(ConversionSchema((json \ "fields_conv").as[List[ConversionField]], (json \ "fields_custom").asOpt[List[CustomField]]))
  }
  implicit lazy val ConversionSchemaWrites: Writes[ConversionSchema] = Writes[ConversionSchema] {
    o => JsObject(Seq("fields_conv" -> Json.toJson(o.fields_conv), "fields_custom" -> Json.toJson(o.fields_custom)).filter(_._2 != JsNull))
  }
  implicit lazy val ConversionFieldReads: Reads[ConversionField] = Reads[ConversionField] {
    json => JsSuccess(ConversionField((json \ "field_std").as[String], (json \ "formula").as[String]))
  }
  implicit lazy val ConversionFieldWrites: Writes[ConversionField] = Writes[ConversionField] {
    o => JsObject(Seq("field_std" -> Json.toJson(o.field_std), "formula" -> Json.toJson(o.formula)).filter(_._2 != JsNull))
  }
  implicit lazy val CustomFieldReads: Reads[CustomField] = Reads[CustomField] {
    json => JsSuccess(CustomField((json \ "name").as[String]))
  }
  implicit lazy val CustomFieldWrites: Writes[CustomField] = Writes[CustomField] {
    o => JsObject(Seq("name" -> Json.toJson(o.name)).filter(_._2 != JsNull))
  }
  implicit lazy val ErrorReads: Reads[Error] = Reads[Error] {
    json => JsSuccess(Error((json \ "code").asOpt[Int], (json \ "message").as[String], (json \ "fields").asOpt[String]))
  }
  implicit lazy val ErrorWrites: Writes[Error] = Writes[Error] {
    o => JsObject(Seq("code" -> Json.toJson(o.code), "message" -> Json.toJson(o.message), "fields" -> Json.toJson(o.fields)).filter(_._2 != JsNull))
  }
  implicit lazy val SuccessReads: Reads[Success] = Reads[Success] {
    json => JsSuccess(Success((json \ "message").as[String], (json \ "fields").asOpt[String]))
  }
  implicit lazy val SuccessWrites: Writes[Success] = Writes[Success] {
    o => JsObject(Seq("message" -> Json.toJson(o.message), "fields" -> Json.toJson(o.fields)).filter(_._2 != JsNull))
  }
  implicit lazy val DatasetReads: Reads[Dataset] = Reads[Dataset] {
    json => JsSuccess(Dataset((json \ "alternate_identifier").asOpt[String], (json \ "author").asOpt[String], (json \ "frequency").asOpt[String], (json \ "holder_identifier").asOpt[String], (json \ "holder_name").asOpt[String], (json \ "identifier").asOpt[String], (json \ "license_id").asOpt[String], (json \ "license_title").asOpt[String], (json \ "modified").asOpt[String], (json \ "name").as[String], (json \ "notes").asOpt[String], (json \ "owner_org").asOpt[String], (json \ "publisher_identifier").asOpt[String], (json \ "publisher_name").asOpt[String], (json \ "relationships_as_object").asOpt[List[Relationship]], (json \ "resources").asOpt[List[Resource]], (json \ "theme").asOpt[String], (json \ "title").asOpt[String], (json \ "privatex").asOpt[Boolean]))
  }
  implicit lazy val DatasetWrites: Writes[Dataset] = Writes[Dataset] {
    o => JsObject(Seq("alternate_identifier" -> Json.toJson(o.alternate_identifier), "author" -> Json.toJson(o.author), "frequency" -> Json.toJson(o.frequency), "holder_identifier" -> Json.toJson(o.holder_identifier), "holder_name" -> Json.toJson(o.holder_name), "identifier" -> Json.toJson(o.identifier), "license_id" -> Json.toJson(o.license_id), "license_title" -> Json.toJson(o.license_title), "modified" -> Json.toJson(o.modified), "name" -> Json.toJson(o.name), "notes" -> Json.toJson(o.notes), "owner_org" -> Json.toJson(o.owner_org), "publisher_identifier" -> Json.toJson(o.publisher_identifier), "publisher_name" -> Json.toJson(o.publisher_name), "relationships_as_object" -> Json.toJson(o.relationships_as_object), "resources" -> Json.toJson(o.resources), "theme" -> Json.toJson(o.theme), "title" -> Json.toJson(o.title), "privatex" -> Json.toJson(o.privatex)).filter(_._2 != JsNull))
  }
  implicit lazy val RelationshipReads: Reads[Relationship] = Reads[Relationship] {
    json => JsSuccess(Relationship((json \ "subject").asOpt[String], (json \ "object").asOpt[String], (json \ "type").asOpt[String], (json \ "comment").asOpt[String]))
  }
  implicit lazy val RelationshipWrites: Writes[Relationship] = Writes[Relationship] {
    o => JsObject(Seq("subject" -> Json.toJson(o.subject), "object" -> Json.toJson(o.`object`), "type" -> Json.toJson(o.`type`), "comment" -> Json.toJson(o.comment)).filter(_._2 != JsNull))
  }
  implicit lazy val ResourceReads: Reads[Resource] = Reads[Resource] {
    json => JsSuccess(Resource((json \ "cache_last_updated").asOpt[String], (json \ "cache_url").asOpt[String], (json \ "created").asOpt[String], (json \ "datastore_active").asOpt[Boolean], (json \ "description").asOpt[String], (json \ "distribution_format").asOpt[String], (json \ "format").asOpt[String], (json \ "hash").asOpt[String], (json \ "id").asOpt[String], (json \ "last_modified").asOpt[String], (json \ "mimetype").asOpt[String], (json \ "mimetype_inner").asOpt[String], (json \ "name").asOpt[String], (json \ "package_id").asOpt[String], (json \ "position").asOpt[Int], (json \ "resource_type").asOpt[String], (json \ "revision_id").asOpt[String], (json \ "size").asOpt[Int], (json \ "state").asOpt[String], (json \ "url").asOpt[String]))
  }
  implicit lazy val ResourceWrites: Writes[Resource] = Writes[Resource] {
    o => JsObject(Seq("cache_last_updated" -> Json.toJson(o.cache_last_updated), "cache_url" -> Json.toJson(o.cache_url), "created" -> Json.toJson(o.created), "datastore_active" -> Json.toJson(o.datastore_active), "description" -> Json.toJson(o.description), "distribution_format" -> Json.toJson(o.distribution_format), "format" -> Json.toJson(o.format), "hash" -> Json.toJson(o.hash), "id" -> Json.toJson(o.id), "last_modified" -> Json.toJson(o.last_modified), "mimetype" -> Json.toJson(o.mimetype), "mimetype_inner" -> Json.toJson(o.mimetype_inner), "name" -> Json.toJson(o.name), "package_id" -> Json.toJson(o.package_id), "position" -> Json.toJson(o.position), "resource_type" -> Json.toJson(o.resource_type), "revision_id" -> Json.toJson(o.revision_id), "size" -> Json.toJson(o.size), "state" -> Json.toJson(o.state), "url" -> Json.toJson(o.url)).filter(_._2 != JsNull))
  }
  implicit lazy val ExtraReads: Reads[Extra] = Reads[Extra] {
    json => JsSuccess(Extra((json \ "key").asOpt[String], (json \ "value").asOpt[String]))
  }
  implicit lazy val ExtraWrites: Writes[Extra] = Writes[Extra] {
    o => JsObject(Seq("key" -> Json.toJson(o.key), "value" -> Json.toJson(o.value)).filter(_._2 != JsNull))
  }
  implicit lazy val StdUrisReads: Reads[StdUris] = Reads[StdUris] {
    json => JsSuccess(StdUris((json \ "label").asOpt[String], (json \ "value").asOpt[String]))
  }
  implicit lazy val StdUrisWrites: Writes[StdUris] = Writes[StdUris] {
    o => JsObject(Seq("label" -> Json.toJson(o.label), "value" -> Json.toJson(o.value)).filter(_._2 != JsNull))
  }
  implicit lazy val TokenReads: Reads[Token] = Reads[Token] {
    json => JsSuccess(Token((json \ "token").asOpt[String]))
  }
  implicit lazy val TokenWrites: Writes[Token] = Writes[Token] {
    o => JsObject(Seq("token" -> Json.toJson(o.token)).filter(_._2 != JsNull))
  }
  implicit lazy val UserReads: Reads[User] = Reads[User] {
    json => JsSuccess(User((json \ "name").asOpt[String], (json \ "email").asOpt[String], (json \ "password").asOpt[String], (json \ "fullname").asOpt[String], (json \ "about").asOpt[String]))
  }
  implicit lazy val UserWrites: Writes[User] = Writes[User] {
    o => JsObject(Seq("name" -> Json.toJson(o.name), "email" -> Json.toJson(o.email), "password" -> Json.toJson(o.password), "fullname" -> Json.toJson(o.fullname), "about" -> Json.toJson(o.about)).filter(_._2 != JsNull))
  }
  implicit lazy val AutocompResReads: Reads[AutocompRes] = Reads[AutocompRes] {
    json => JsSuccess(AutocompRes((json \ "match_field").asOpt[String], (json \ "match_displayed").asOpt[String], (json \ "name").asOpt[String], (json \ "title").asOpt[String]))
  }
  implicit lazy val AutocompResWrites: Writes[AutocompRes] = Writes[AutocompRes] {
    o => JsObject(Seq("match_field" -> Json.toJson(o.match_field), "match_displayed" -> Json.toJson(o.match_displayed), "name" -> Json.toJson(o.name), "title" -> Json.toJson(o.title)).filter(_._2 != JsNull))
  }
  implicit lazy val CredentialsReads: Reads[Credentials] = Reads[Credentials] {
    json => JsSuccess(Credentials((json \ "username").asOpt[String], (json \ "password").asOpt[String]))
  }
  implicit lazy val CredentialsWrites: Writes[Credentials] = Writes[Credentials] {
    o => JsObject(Seq("username" -> Json.toJson(o.username), "password" -> Json.toJson(o.password)).filter(_._2 != JsNull))
  }
}
