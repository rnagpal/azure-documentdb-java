## Changes in 1.5.1 : ##

- Fixed a bug in HashPartitionResolver to generate hash values in little-endian order to be consistent with other SDKs.

## Changes in 1.5.0 : ##

- Added Client-side sharding framework to the SDK. Implemented HashPartionResolver and RangePartitionResolver classes.

## Changes in 1.4.0 : ##

- Implement Upsert. New upsertXXX methods added to support Upsert feature.
- Implement ID Based Routing. No public API changes, all changes internal.

## Changes in 1.3.0 : ##

- Release skipped to bring version number in alignment with other SDKs

## Changes in 1.2.0 : ##

- Supports GeoSpatial index.
- Validates id property for all resources. Ids for resources cannot contain ?, /, #, \\, characters or end with a space.
- Adds new header "index transformation progress" to ResourceResponse.

## Changes in 1.1.0 : ##

- Implements V2 indexing policy