db = db.getSiblingDb('ms_specialties_db');

// COLECCIÓN: specialties
db.createCollection('specialties', {
   validator: {
      $jsonSchema: {
         bsonType: 'object',
         required: ['code', 'name', 'status', 'createdAt', 'updatedAt'],
         properties: {
            code: { bsonType: 'string', description: 'Código único de la especialidad (Ej: EDUC-001)' },
            name: { bsonType: 'string', description: 'Nombre de la especialidad' },
            description: { bsonType: 'string' },
            color: { bsonType: 'string' },
            status: { enum: ['ACTIVE', 'INACTIVE'], description: 'Estados permitidos por CommonStatus' },
            createdAt: { bsonType: 'date' },
            updatedAt: { bsonType: 'date' }
         }
      }
   }
});

// Índices optimizados para búsquedas reactivas de alta concurrencia
db.specialties.createIndex({ "code": 1 }, { unique: true, name: "idx_specialties_code_unique" });
db.specialties.createIndex({ "status": 1 }, { name: "idx_specialties_status" });


// COLECCIÓN: client_types
db.createCollection('client_types', {
   validator: {
      $jsonSchema: {
         bsonType: 'object',
         required: ['name', 'status', 'createdAt', 'updatedAt'],
         properties: {
            name: { bsonType: 'string', description: 'Nombre del tipo de cliente (Ej: SIS, PARTICULAR)' },
            description: { bsonType: 'string' },
            status: { enum: ['ACTIVE', 'INACTIVE'] },
            createdAt: { bsonType: 'date' },
            updatedAt: { bsonType: 'date' }
         }
      }
   }
});

db.client_types.createIndex({ "name": 1 }, { unique: true, name: "idx_client_types_name_unique" });


// COLECCIÓN: treatments
db.createCollection('treatments', {
   validator: {
      $jsonSchema: {
         bsonType: 'object',
         required: ['code', 'name', 'specialtyId', 'status', 'createdAt', 'updatedAt'],
         properties: {
            code: { bsonType: 'string', description: 'Código único del tratamiento (Ej: TRAT-001)' },
            name: { bsonType: 'string', description: 'Nombre del tratamiento médico o terapia' },
            specialtyId: { bsonType: 'string', description: 'ID de la especialidad a la que pertenece' },
            status: { enum: ['ACTIVE', 'INACTIVE'] },
            createdAt: { bsonType: 'date' },
            updatedAt: { bsonType: 'date' }
         }
      }
   }
});

db.treatments.createIndex({ "code": 1 }, { unique: true, name: "idx_treatments_code_unique" });
db.treatments.createIndex({ "specialtyId": 1 }, { name: "idx_treatments_specialty_fk" });
