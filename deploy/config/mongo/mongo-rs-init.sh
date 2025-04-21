echo "Initializing replicaset (without auth)"
mongosh --host mongo-primary:27017 <<EOF
  var cfg = {
    "_id": "rs0",
    "members": [
      { _id: 0, host: "mongo-primary:27017" },
      { _id: 1, host: "mongo-secondary1:27017" },
      { _id: 2, host: "mongo-secondary2:27017" }
    ]
  };
  rs.initiate(cfg);
EOF

echo "Waiting for primary election..."

echo "Creating user..."
mongosh --host mongo-primary:27017 <<EOF
use admin;
db.createUser({
  user: "admin",
  pwd: "secret",
  roles: [ { role: "root", db: "admin" } ]
});
EOF

echo "Done"